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
 - The following vue component was designed to display data in a grid of "baseball cards"
 - instead of a list in a data table. This is especially useful if there is an image
 - or video that needs to be shown with the data. The mock-up example for the Card Grid is
 - on the bottom of the homepage located at https://f2p9s0.axshare.com/#id=quh8s3&p=home
 -->

<template>
    <div>
        <div class="element-list-header">
            <div v-if="cardConfiguration.cardSettings.tableHeading" class="page-header-left bold-text">{{cardConfiguration.cardSettings.tableHeading}}</div>
            <div v-if="cardConfiguration.cardSettings.tableDescription" class="page-header-left">{{cardConfiguration.cardSettings.tableDescription}}</div>
            <div class="page-header-right">
                <div class="small-header-text">{{paginationText}}</div>
            </div>
        </div>
        <v-data-iterator
            :items="data"
            :items-per-page="itemsPerPage"
            :page.sync="page"
            @pagination="updateHeaderPagination($event)"
            :sort-by="sortBy"
            :sort-desc="sortDesc"
            :loading="loading"
            :loading-text="LOADINGMESSAGE"
            :no-data-text="statusMessage ? statusMessage : NODATAMESSAGE"
            hide-default-footer
        >
            <template v-slot:header v-if="!hideHeader">
                <div class="grid-header-wrapper">
                    <div class="grid-header-row">
                        <div class="grid-header-menu">
                            <span>Order By: </span>
                            <SelectDropdown :options="cardConfiguration.cardKeys" :selectedOption="sortBy"
                                :selectDisplay="headerSelectDisplay" @selectDropdownChange="updateSortBy($event.target.value)"
                            >
                            </SelectDropdown>
                            <div class="icon-wrapper-div">
                                <v-icon :class="sortIconClass" @click="updateSortOrderDisplay()">mdi-arrow-up</v-icon>
                            </div>
                        </div>
                        <div v-if="loading===true" class="grid-header-loading">
                            <v-progress-linear indeterminate></v-progress-linear>
                        </div>
                    </div>
                </div>
            </template>
            <template v-slot:default="props">
                <v-row>
                    <v-col v-for="(item, index) in props.items" :key="index" cols="12" md="2">
                        <v-card class="element-card-display">
                            <!--Card image-->
                            <div class="card-icon-div">
                                <v-icon class="card-icon-style">
                                    {{getCardIcon(item[cardConfiguration.cardSettings.iconKey])}}
                                </v-icon>
                            </div>
                            <!--Card title-->
                            <div class="element-card-row-heading element-card-space"
                                 :class="{'theme-color-text': sortBy === cardConfiguration.exclusiveCardKeys.title}">
                                <router-link
                                    :to="buildRoute(
                                        item[cardConfiguration.cardSettings.cardKey],
                                        VUEROUTES.cluster,
                                        getCategory(item)
                                    )"
                                    class="element-card-header"
                                    @click.native="saveSelectedData(item)"
                                >
                                    {{item[cardConfiguration.exclusiveCardKeys.title]}}
                                </router-link>
                            </div>
                            <div :class="{'theme-color-text': sortBy === cardConfiguration.exclusiveCardKeys.type}">
                                {{item[cardConfiguration.exclusiveCardKeys.type]}}
                            </div>
                            <!--Remaining lists and keys-->
                            <div class="card-list-display">
                                <div>
                                    <div v-for="(key, kIndex) in cardConfiguration.cardKeys"
                                         :key="key + index + '_' + kIndex"
                                         :class="{'theme-color-text': sortBy === key}">
                                        <!--Display the remaining keys that are not in the exclusiveCardKeys-->
                                        <div v-if="!(groupExclusiveCardKeys.includes(key))" class="element-card-space">
                                            <span v-if="isIdField(key)">
                                                <router-link
                                                    :to="buildRoute(
                                                        getUri(item, key),
                                                        VUEROUTES.element,
                                                        getCategory(item)
                                                    )"
                                                    class="element-card-row-heading element-card-header"
                                                    @click.native="saveSelectedData(item)"
                                                >
                                                    {{getFieldValue(item, key)}}
                                                </router-link>
                                            </span>
                                            <span v-else>
                                                {{getFieldTitle(key)}} : {{getFieldValue(item, key)}}
                                            </span>
                                        </div>
                                    </div>
                                    <div v-for="(list) in cardConfiguration.exclusiveCardKeys.listDisplay"
                                         :key="list.field + index">
                                        <div v-for="(listItem, lIndex) in getListItem(item, list.field)"
                                             :key="lIndex" :class="{'theme-color-text': sortBy === list.field}">
                                            <span v-if="list.value">
                                                {{listItem[list.field]}}: {{listItem[list.value]}}
                                            </span>
                                            <span v-else>
                                                {{listItem}}
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div v-for="(counter) in cardConfiguration.exclusiveCardKeys.countDisplay" class="element-card-space"
                                     :key="counter.field + index" :class="{'theme-color-text': sortBy === counter.value}">
                                    {{counter.title}}: {{getListCount(item, counter.value)}}
                                </div>
                            </div>
                            <!--Card footer keys-->
                            <!-- TODO: Add footer back in if we get dates in the dataset
                            <div class="element-card-footer">
                                <span :class="{'theme-color-text': sortBy === cardConfiguration.exclusiveCardKeys.date}">{{item[cardConfiguration.exclusiveCardKeys.date]}}</span>
                            </div>-->
                        </v-card>
                    </v-col>
                </v-row>
            </template>
            <template v-slot:footer v-if="!hideFooter">
                <div class="pagination-footer">
                    <div class="pagination-footer-text">Results per page:
                        <SelectDropdown :options="itemsPerPageArray" :selectedOption="paging ? paging.itemsPerPage : null"
                                        @selectDropdownChange="updateItemsPerPage($event)"
                        >
                        </SelectDropdown>
                    </div>
                    <v-pagination
                            class="pagination-component"
                            v-model="page"
                            :length="pageCount"
                            :value="selectedPage"
                            total-visible="10"
                            @input="updatePageSelection($event)"
                    ></v-pagination>
                    <div class="pagination-footer-text-end">{{paginationText}}</div>
                </div>
            </template>
        </v-data-iterator>
    </div>
</template>

<script>
    import SelectDropdown from '@/components/SelectDropdown';
    import DataDisplayMixin from '@/mixins/DataDisplayMixin';
    import {SELECTDISPLAY, VUEROUTES, TABLEFIELDMAPPING, LOADINGMESSAGE, DATATYPES, NODATAMESSAGE, buildRoute, isAnObject} from '../utils'
    import {mapMutations} from 'vuex';

    export default {
        name: 'CardGrid',
        mixins: [DataDisplayMixin],
        props: {
            cardConfiguration: Object,
            statusMessage: String
        },
        data: () => ({
            itemsPerPageArray: [12, 24, 48, 96, 'All'],
            headerSelectDisplay: '',
        }),
        created() {
            this.VUEROUTES = VUEROUTES;
            this.TABLEFIELDMAPPING = TABLEFIELDMAPPING;
            this.LOADINGMESSAGE = LOADINGMESSAGE;
            this.NODATAMESSAGE = NODATAMESSAGE;
            this.headerSelectDisplay = SELECTDISPLAY.button;
            this.buildRoute = buildRoute;
        },
        components: {SelectDropdown},
        computed: {
            groupExclusiveCardKeys() {
                const getObjectValues = obj => isAnObject(obj) ? Object.values(obj).map(getObjectValues).flat() : [obj];
                return getObjectValues(this.cardConfiguration.exclusiveCardKeys);
            },
            sortBy() {
                return this.sort.by !== undefined ? this.sort.by : this.cardConfiguration.cardKeys[0];
            },
            sortDesc() {
                return this.sort.descending !== undefined ? this.sort.descending : false;
            },
            sortIconClass() {
                return this.sortDesc ? 'arrow-icon-down' : 'arrow-icon-up';
            }
        },
        methods: {
            ...mapMutations(['updateDataSelection']),
            updateSortOrderDisplay() {
                let validVal = !!this.sortDesc;
                this.updateSortDesc(!validVal);
            },
            getCardIcon(type) {
                for (const key in this.cardConfiguration.cardIcons) {
                    if (this.cardConfiguration.cardIcons.hasOwnProperty(key) && type.includes(key)) {
                        return this.cardConfiguration.cardIcons[key].icon;
                    }
                }
                return 'mdi-card-text-outline';
            },
            getCategory(item) {
                return item[TABLEFIELDMAPPING.category] ?
                    item[TABLEFIELDMAPPING.category] :
                    item[TABLEFIELDMAPPING.prototype] ?
                        item[TABLEFIELDMAPPING.prototype][TABLEFIELDMAPPING.category] :
                        DATATYPES.eventType.id;
            },
            getFieldValue(item, key) {
                return item[key] ? item[key] : item[TABLEFIELDMAPPING.prototype] ? item[TABLEFIELDMAPPING.prototype][key] : '';
            },
            getListItem(item, field) {
                return item[field] ? item[field] : item[TABLEFIELDMAPPING.prototype] ? item[TABLEFIELDMAPPING.prototype][field] : {}
            },
            getListCount(item, field) {
                let value = this.getFieldValue(item, field);
                return value ? value : 0;
            },
            isIdField(field) {
                return [TABLEFIELDMAPPING.id, TABLEFIELDMAPPING.idUri, TABLEFIELDMAPPING.prototypeId, TABLEFIELDMAPPING.prototypeUri].includes(field);
            },
            getUri(item, key) {
                return key === TABLEFIELDMAPPING.id ? item[TABLEFIELDMAPPING.idUri] :
                    key === TABLEFIELDMAPPING.prototypeId ? item[TABLEFIELDMAPPING.prototypeUri] : '';
            },
            saveSelectedData(data) {
                this.updateDataSelection(data);
            }
        }
    }
</script>

<style lang="scss" scoped>

    .grid-header-wrapper {
        background-color: $theme-teal;
        color: #FFFFFF;
        border-radius: 4px 4px 0 0;
        @extend .bold-text;
    }

    .grid-header-row {
        @include flex-row-wrap;
    }

    .grid-header-menu {
        @include flex-row-nowrap;
        padding: 16px;
        width: 100%;
        height: 40px;
        align-items: center;
        justify-content: flex-start;

        .select-css {
            background-color: #FFFFFF;
            font-weight: initial;
        }

        span {
            padding-right: 10px;
            white-space: nowrap;
        }

        div .v-icon {
            color: #FFFFFF;
            font-size: 18px;
        }
    }

    .grid-header-loading{
        width: 100%;
    }

    .element-card-display {
        padding: 16px;
    }

    .element-card-icon {
        @extend .dark-icon-button;
        @extend .v-icon;
        padding: 0;
        font-size: 32px;
    }

    .element-card-row-heading {
        @extend .row-heading;
        //margin-bottom: 16px;
    }

    .element-card-header {
        color: inherit;
        @include no-text-decoration;
        @extend .page-link;
    }

    .element-card-space {
        padding-bottom: 16px;
    }

    .element-card-footer {
        @include flex-row-wrap;
        padding-top: 16px;
        justify-content: space-between;

        span {
            overflow-wrap: break-word;
            width: 100%;
        }
    }

    .card-list-display {
        @include flex-column-nowrap;
        width: 100%;
        justify-content: space-between;
    }

    .card-icon-div {
        display: flex;
        justify-content: center;
        padding: 10px 0;
    }

    .card-icon-style {
        font-size: 80px;
    }

    .arrow-icon-down {
        transform: rotate(180deg);
        transition: all .3s ease;
    }

    .arrow-icon-up {
        transform: rotate(0deg);
        transition: all .3s ease;
    }
</style>
