
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

import Vue from 'vue';
import Vuex from 'vuex';
import {TABLESELECTIONS} from './utils';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        filters: {},
        dataSelection: {},
        knowledgeBase: {},
        tableSelections: {}, //{} - selectedTab, selectedDisplay, sort(sortBy, sortDesc), paging(resultsPerPage, pageNumber, pageStart)
        filterReset: false,
        filterSubmission: false,
        //TODO: The following variables will be removed and functionality replaced with an API request once filters are being saved in a database
        savedUrlParameters: {}, //{} - routeName : parameters, used for querying results without having to re-enter a search term or re-populate filter fields
        savedFilters: {}, //{} - id : filterList, used for re-populating filter fields in the filter menu
    },
    mutations: {
        updateSingleFilters(state, update) {
            state.filters = { ...state.filters, [update.type]: update.filter };
        },
        updateSavedFilters(state, update) {
            state.savedFilters[update.id] = state.filters;
        },
        removeFiltersByType(state, type) {
            state.filters = {...state.filters};
            delete state.filters[type];
        },
        removeAllFilters(state) {
            state.filters = {};
        },
        resetFilters(state, filters) {
            state.filters = filters;
        },
        updateFilterReset(state, boolVal) {
            state.filterReset = boolVal;
        },
        updateFilterSubmission(state, boolVal) {
            state.filterSubmission = boolVal;
        },
        updateDataSelection(state, dataSelection) {
            state.dataSelection = dataSelection;
        },
        updateSavedUrlParameters(state, query) {
            state.savedUrlParameters = { ...state.savedUrlParameters, [query.name]: query.params };
        },
        removeSavedUrlParametersByRoute(state, route) {
            state.savedUrlParameters = {...state.savedUrlParameters};
            delete state.savedUrlParameters[route];
        },
        removeSavedUrlParametersByName(state, query) {
            state.savedUrlParameters = {...state.savedUrlParameters};
            delete state.savedUrlParameters[query.route][query.parameter];
        },
        addSelections(state, update) {
            state.tableSelections = { ...state.tableSelections, [update.option]: update.value };
        },
        addSortOption(state, update) {
            state.tableSelections = { ...state.tableSelections, [TABLESELECTIONS.sort]: { ...state.tableSelections[TABLESELECTIONS.sort], [update.option]: update.value }};
        },
        addPagingOption(state, update) {
            state.tableSelections = { ...state.tableSelections, [TABLESELECTIONS.paging]: { ...state.tableSelections[TABLESELECTIONS.paging], [update.option]: update.value }};
        },
        removeSelectionsByOption(state, option) {
            state.tableSelections = {...state.tableSelections};
            delete state.tableSelections[option];
        }
    },
    actions: {
        updateFilters({ commit }, update) {
            commit('updateSingleFilters', update);
        },
        updateUrlParameters({ commit }, update) {
            if(update.params) {
                commit('updateSavedUrlParameters', {name: update.name, params: update.params});
            }
            else {
                commit('removeSavedUrlParametersByRoute', update.name);
            }

        },
        removeFilter({ commit }, update) {
            if(update.type) {
                commit('removeFiltersByType', update.type);
            }
            else {
                console.warn('Deletion message not formatted correctly. Must have a type')
            }
        },
        updateTableSelections({ commit, state }, update) {
            const viewFields = [TABLESELECTIONS.display];
            const sortFields = [TABLESELECTIONS.sortBy, TABLESELECTIONS.sortDesc];
            const pagingFields = [TABLESELECTIONS.pageNumber, TABLESELECTIONS.pageStart, TABLESELECTIONS.itemsPerPage];

            if(viewFields.includes(update.option)) {
                //loop through state and remove all of the options that are not tab or display
                for (let selection of Object.keys(state.tableSelections)) {
                    if(!viewFields.includes(selection)) {
                        commit('removeSelectionsByOption', selection);
                    }
                }
            }

            //add new selection
            commit(
                sortFields.includes(update.option) ? 'addSortOption' :
                    pagingFields.includes(update.option) ? 'addPagingOption' :
                    'addSelections',
                update
            );
        }
    },
    getters: {
        getFilterKeys: state => Object.keys(state.filters),
        getFilterByType: state => filter => state.filters[filter],
        getSavedFilterByTypeAndIndex: state => (id, type, index) => state.savedFilters[id][type][index],
        getQueryParams: state => ({
            filter: state.filters,
            url: state.savedUrlParameters
        })
    }
});

