<!--
  - Copyright 2019 Next Century Corporation/CACI
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -       http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -
  -->
<!--
 - The following vue component is a basic table view with no special features.
 - It can be used as a stand-alone data table display. It is also used as a
 - nested table in the expansion row of the DataTable component.
 -->
<template>
    <div>
        <div v-if="loading===true">
            <v-progress-linear indeterminate></v-progress-linear>
        </div>
        <div v-if="statusMessage !== ''"  class="loading-message-gray">{{statusMessage}}</div>
        <div v-else :class="scrollOn ? 'grid-container grid-scroll-display' : 'grid-container'" :style="gridStyle">
            <div v-for="(item, index) in data" :key="index">
                <!--display headers-->
                <div v-if="index === 0" class="grid-container-header">
                    <div v-if="headers.length > 0">
                        <span v-for="(header, hIndex) in headers" :key="hIndex">
                            {{header.text}}
                        </span>
                    </div>
                    <div v-else>
                        <span v-for="(field, fIndex) in Object.keys(item)" :key="fIndex">
                            {{field}}
                        </span>
                    </div>
                </div>
                <!--display data rows-->
                <div class="grid-container-rows">
                    <div v-if="headers.length > 0 || isAnObject(item)">
                        <span v-for="(row, rIndex) in (headersExist ? headers : item)" :key="rIndex">
                            <span v-if="isIdField(row.value, rIndex)">
                                <span v-if="row.value === TABLEFIELDMAPPING.docId">
                                    <router-link :to="buildRoute(item[row.value], VUEROUTES.doc)"
                                            class="theme-color-text page-link"
                                            target="_blank"
                                    >
                                        {{getRowValue(item[row.value], row)}}
                                    </router-link>
                                </span>
                                <span v-else>

                                    <a :href="'/#/claims/topic/' + item.id" target="_blank" rel="noopener noreferrer" class="theme-color-text page-link" >See Details</a>

                                </span>
                            </span>
                            <span v-else-if="Array.isArray(getRowValue(item[row.value], row))" class="kb-description-wrapper">
                                <span v-for="(values, vIndex) in getRowValue(item[row.value], row)" :key="vIndex">
                                    <span v-if="isIdArray(row.value)">
                                        <router-link
                                                :to="buildRoute(values[TABLEFIELDMAPPING.idUri],
                                            VUEROUTES.cluster, item[TABLEFIELDMAPPING.category])"
                                                class="theme-color-text page-link"
                                                target="_blank"
                                        >
                                            {{values[TABLEFIELDMAPPING.id]}}
                                        </router-link>
                                    </span>
                                    <span v-else>{{values}}</span>
                                </span>
                            </span>
                            <span v-else-if="row.value === TABLEFIELDMAPPING.claimer" class="kb-description-wrapper">
                                    <span>
                                        <!-- <router-link
                                                :to="buildRoute(getRouteValue(item, row),
                                            route, item[TABLEFIELDMAPPING.category])"
                                                class="theme-color-text page-link"
                                        >
                                           {{getRowValue(item[row.value], row)}}
                                        </router-link> -->
                                        <!-- <a :href="'/#/provenance/doc/' + item.sourceDoc" target="_blank" rel="noopener noreferrer" class="theme-color-text page-link" >{{getRowValue(item[row.value], row)}}</a> -->
                                        {{getRowValue(item[row.value], row)}}
                                    </span>
                            </span>
                            <span v-else-if="row.value === TABLEFIELDMAPPING.sorceDocTitleExt" class="kb-description-wrapper">
                                    <span>
                                        <a :href="'/#/provenance/doc/' + item.sourceDoc" target="_blank" rel="noopener noreferrer" class="table-row-close" >{{item.sourceDocTitle}}</a>
                                        <br/>
                                        "{{item.description}}"
                                    </span>
                            </span>                                                        
                            <span v-else>{{getRowValue(item[row.value], row)}}</span>
                        </span>
                    </div>
                    <span v-else>{{item}}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { TABLEFIELDMAPPING, VUEROUTES, isAnObject, buildRoute} from '../utils';
    import DataDisplayMixin from '@/mixins/DataDisplayMixin';

    export default {
        name: 'SimpleTable',
        mixins: [DataDisplayMixin],
        props: {
            headers: Array,
            statusMessage: {
                type: String,
                default: ''
            },
            route: String,
            scrollOn: {
                type: Boolean,
                default: true
            }
        },
        data() {
            return {
                headersExist: this.headers.length > 0
            }
        },
        created() {
            this.TABLEFIELDMAPPING = TABLEFIELDMAPPING;
            this.VUEROUTES = VUEROUTES;
            this.buildRoute = buildRoute;
            this.isAnObject = isAnObject;
        },
        computed: {
            gridStyle() {
                return {
                    gridTemplateColumns: `repeat(${this.headers.length}, auto)`
                }
            },
        },
        methods: {
            isIdField(headerField, objectField) {
                let idFields = [this.TABLEFIELDMAPPING.prototypeId, this.TABLEFIELDMAPPING.id, this.TABLEFIELDMAPPING.docId];
                return this.headersExist ?
                    idFields.includes(headerField) :
                    idFields.includes(objectField);
            },
            isIdArray(field) {
                let idArray = [this.TABLEFIELDMAPPING.clusterIdList];
                return idArray.includes(field);
            },
            getRowValue(headerValue, objectValue) {
                return this.headersExist ? headerValue : objectValue;
            },
            getRouteValue(headerValue, objectValue) {
                return this.headersExist && headerValue[TABLEFIELDMAPPING.idUri] ? headerValue[TABLEFIELDMAPPING.idUri] :
                    this.headersExist && headerValue[TABLEFIELDMAPPING.claimId] ? headerValue[TABLEFIELDMAPPING.claimId] :
                    this.headersExist && headerValue[TABLEFIELDMAPPING.clusterUri] ? headerValue[TABLEFIELDMAPPING.clusterUri] : objectValue;
            }
        }
    }
</script>

<style lang="scss" scoped>
    .loading-message-gray {
        @include flex-row-nowrap;
        justify-content: center;
        color: rgba(0, 0, 0, 0.38);
        height: 60px;
        padding: 16px;
        background-color: $background-pale-gray;
    }

    .grid-container {
        display: grid;

        div {
            display: contents;
        }

        span {
            height: auto !important;
        }

        .grid-container-header span {
            background-color: $background-pale-teal;
        }

        :last-child > div > span {
            border-bottom: $background-pale-teal solid 1px;
        }

        div > span:nth-child(-n+5) {
            padding: 6px 10px;
        }
    }
</style>
