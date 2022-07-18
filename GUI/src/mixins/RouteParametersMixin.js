/*
 * Copyright 2019 Next Century Corporation/CACI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

export default {
    methods: {
        updateRouteParams(queryParam, queryValue, routeName) {
            let updatedQuery = {[queryParam] : decodeURIComponent(queryValue)};
            let reRoute = this.$route.name !== routeName ? routeName : null;

            if (!queryValue) {
                this.deleteParams(queryParam);
            }
            else if (this.routeParamExists(queryParam)) {
                this.replaceParams(queryParam, updatedQuery, reRoute);
            } else {
                this.addParams(updatedQuery, reRoute)
            }
        },
        deleteParams(queryParam) {
            //If there is no query value then remove the URL parameter
            let query = Object.assign({}, this.$route.query);
            if(Object.keys(query).length > 0 && Object.keys(query).includes(queryParam)) {
                delete query[queryParam];
                return this.$router.replace({query})
                    .then(() => {
                        const urlParams = this.$store.state.savedUrlParameters[this.$route.name];
                        if(urlParams && urlParams.hasOwnProperty(queryParam)) {
                            this.$store.commit('removeSavedUrlParametersByName', {route:this.$route.name, parameter:queryParam});
                        }
                    }).catch(e => console.warn('Unchanged URL Route', e));
            }
        },
        replaceParams(queryParam, updatedQuery, routeName) {
            //If the value has changed for the route or query parameter, update and replace them
            let params = {};
            if(routeName && routeName !== this.$route.name) {
                params['name'] = routeName;
                params['query'] = updatedQuery;
            }
            else if(updatedQuery[queryParam] !== this.$route.query[queryParam]) {
                let filteredQueryParams = Object.keys(this.$route.query).filter(key => !key.includes(queryParam)).reduce((obj, key) => {
                    obj[key] = this.$route.query[key];
                    return obj;
                }, {});
                params = {query: Object.assign({}, filteredQueryParams, updatedQuery)};
            }

            if(Object.keys(params).length > 0) {
                return this.$router.replace(params)
                    .then(() => {
                        this.$store.dispatch('updateUrlParameters', {name: this.$route.name, params: this.$route.query})
                            .catch(e => console.warn('Problem replacing store params:', e));
                    }).catch(e => console.warn('Problem replacing route params', e));
            }
        },
        addParams(updatedQuery, routeName) {
            let params = {query: Object.assign({}, this.$route.query, updatedQuery)};
            if(routeName) {
                params['name'] = routeName;
            }
            return this.$router.push(params)
                .then(() => {
                    this.$store.dispatch('updateUrlParameters', {name: this.$route.name, params: this.$route.query})
                        .catch(e => console.warn('Problem updating store params:', e));
                }).catch((e) => console.warn('Problem updating route params', e));
        },
        routeParamExists(parameter) {
            return this.$route.query && this.$route.query.hasOwnProperty(parameter);
        }
    }
};

