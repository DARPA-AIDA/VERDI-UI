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
 - The following vue component offers a multi-use data table that includes features such
 - as data links, row highlighting, data comparison, "pill" filtering, custom sorting,
 - column hiding, and nested tables. The component is located on the Claims List in the UI
 - at http://localhost:8081/#/claims and shown in the Claims Details mock-up located at
 - https://vzv88e.axshare.com/#id=l8938s&p=claim_frame_details&g=1
 -->

<template>
    <div>
        <div class="element-list-header">
            <!--If there is both a tableHeading and tableDescription-->
            <div v-if="tableConfiguration.tableSettings.tableHeading && tableConfiguration.tableSettings.tableDescription"
                 class="page-header-left">
                <span class="bold-text">{{tableConfiguration.tableSettings.tableHeading}}</span>
                <span class="space-description-left ">{{tableConfiguration.tableSettings.tableDescription}}</span>
            </div>
            <!--Else if there is a tableHeading or tableDescription-->
            <div v-else class="page-header-left">
                <span v-if="tableConfiguration.tableSettings.tableHeading" class="bold-text">{{tableConfiguration.tableSettings.tableHeading}}</span>
                <span v-if="tableConfiguration.tableSettings.tableDescription">{{tableConfiguration.tableSettings.tableDescription}}</span>
            </div>

            <!--Activates filter buttons to filter tables-->
            <div v-if="tableConfiguration.tableSettings.rowfilter" class="page-header-right">
                <span v-for="filter in claimsGetFilterData(data)" :key="filter">
                    <button :class="claimsSetFilterClass(filter)" @click="claimsSetHideRowsList(filter)">
                        {{ filter }}
                    </button>
                </span>
            </div>

            <div class="page-header-right">
                <!--Prints table results count using the DataDisplayMixin-->
                <div class="small-header-text">{{paginationText}}</div>
            </div>
        </div>
        <div v-if="!hideData">
            <!--Activates filter pills to hide columns in the data table-->
            <div v-if="tableConfiguration.tableSettings.hideableColumns && this.hiddenHeaders.length > 0" class="hideable-column-header">
                <span class="bold-text">Filtered Columns: </span>
                <div v-for="col in this.hiddenHeaders" :key="col">
                    <button class="filter-pill-on" @click="showColumn(col)">
                        {{ col }}
                        <v-icon class="show-icon light-icon-button">mdi-close-circle-outline</v-icon>
                    </button>
                </div>
                <span @click="resetColumn()" style="cursor: pointer;"> <u>reset</u> </span>
            </div>
            <!--Set up for the actual Data Table-->
            <v-data-table
                ref="data_table"
                :headers="tableConfiguration.primaryHeaders"
                :items="data"
                :single-expand="singleExpand"
                :expanded.sync="expanded"
                :show-expand="tableConfiguration.tableSettings.expand"
                :item-key="tableConfiguration.tableSettings.rowKey"
                :item-class="setItemClass"
                :sort-by=customSortBy
                :sort-desc=customSortDesc
                :custom-sort="customSort"
                :page.sync="page"
                :items-per-page="itemsPerPage"
                @current-items="$emit('currentItems', $event)"
                @click:row="$emit('updateItem', $event)"
                @item-expanded="$emit('updateItem', $event.item)"
                @pagination="updateHeaderPagination($event)"
                hide-default-footer
                :hide-default-header=true
                :loading=loading
                :no-data-text="statusMessage ? statusMessage : NODATAMESSAGE"
                :loading-text="LOADINGMESSAGE"
            >
                <!--Prints column headers for the data table. Checks for column headers that need to be hidden and activates column sorting-->
                <template v-slot:header="{ props:{ headers } } ">
                    <thead v-if="true">
                        <tr>
                            <th v-for="(header) in headers"
                                scope="col"
                                :key="header.value"
                                class="text-start sortable"
                                v-bind:style="'width: ' + header.width + '; min-width: ' + header.width"
                            >
                                <span v-if="!hiddenHeaders.includes(header.text) && header.sortable">
                                    {{ header.text }}
                                    <v-icon
                                        @click="sortUpdate(data, header.value, customSortDesc)"
                                        :class="sortIconClass(header.value)"
                                     >
                                        {{ customSortBy === header.value ? 'mdi-arrow-down' : 'mdi-sort' }}
                                     </v-icon>
                                    <v-icon v-if="header.hideable" class="hide-icon light-icon-button" @click="hideColumn(header.text)">mdi-eye-off-outline</v-icon>
                                </span>
                            </th>
                        </tr>
                    </thead>
                </template>
                <!--Prints row data. There is some data that is formatted differently based on the column name -->
                <template v-slot:item="{ item, headers, expand, isExpanded }">

                    <!--Highlight entire row when hovered over. The item is expected to include a TABLEFIELDMAPPING.highlight field in order to activate the highlighting-->
                    <tr :class="setItemClass(item)"
                        @mouseover="item.hasOwnProperty(TABLEFIELDMAPPING.highlight) ? item[TABLEFIELDMAPPING.highlight] = turnHighlightOn(item, true) : false"
                        @mouseleave="item.hasOwnProperty(TABLEFIELDMAPPING.highlight) ? item[TABLEFIELDMAPPING.highlight] = turnHighlightOn(item, false) : false"
                        @click.prevent="updateOnClick(item)"
                        :style="claimsShowHideRows(item)"
                    >
                        <td v-for="(header, hIndex) in headers" :key="hIndex" :class="'text-'+header.align">
                            <div v-if="!hiddenHeaders.includes(header.text)">
                                <!--Begins column-specific formatting-->
                                <div v-if="header.value === TABLEFIELDMAPPING.headline">
                                    <!--Table headline column link - The slot for headline is not dynamic. Currently it is expecting a field called 'headline' in order for this template to work. The dataset will need to be modified to contain this field -->
                                    <router-link
                                            :class="isRowExpanded(item) ? 'table-row-open' : 'table-row-close'"
                                            @click.native="saveSelectedItem(item)"
                                                    :to="buildRoute(
                                                    item[tableConfiguration.tableSettings.rowKey],
                                                    item[TABLEFIELDMAPPING.category] === DATATYPES.topic.id ? VUEROUTES.claims: VUEROUTES.claimDetails,
                                                    item[TABLEFIELDMAPPING.category],
                                                    routeParameters)"
                                    >
                                        {{item.headline}}
                                    </router-link>
                                    
                                    <!-- <a :href="'/#/claims/topic/' + item[tableConfiguration.tableSettings.rowKey]" target="_blank" rel="noopener noreferrer" class="theme-color-text page-link" >{{item.headline}}</a> -->
                                    
                                    <!--Compare icon within headline column shown when row is highlighted - space added to column to compensate for the space that the compare icon takes up-->
                                    <div v-if="!item[TABLEFIELDMAPPING.highlight]" class="space-div"></div>
                                    <div v-else-if="item[TABLEFIELDMAPPING.highlight]">
                                        <v-icon :class="!item[TABLEFIELDMAPPING.compare] ? 'table-header-icon' : 'table-header-icon-selected'">
                                            mdi-compare
                                        </v-icon>
                                    </div>
                                </div>
                                <div v-else-if="header.category === TABLEFIELDMAPPING.xvariable">
                                    <!--Table id column link-->
                                        <a href='' target="_blank" >{{item[TABLEFIELDMAPPING.id]}}</a>
                                </div>
                                <div v-else-if="header.value === TABLEFIELDMAPPING.docTitle">
                                    <router-link
                                            :class="isRowExpanded(item) ? 'table-row-open' : 'table-row-close'"
                                            @click.native="saveSelectedItem(item)"
                                                    :to="buildRoute(
                                                    item[tableConfiguration.tableSettings.rowKey],
                                                    VUEROUTES.doc,
                                                    null,
                                                    routeParameters)" 
                                    >
                                        {{item[TABLEFIELDMAPPING.docTitle]}}
                                    </router-link>
                                    
                                    <!-- <a :href="'/#/provenance/doc/' + item[tableConfiguration.tableSettings.rowKey]" target="_blank" rel="noopener noreferrer" class="theme-color-text page-link" >{{item[TABLEFIELDMAPPING.docTitle]}}</a> -->

                                </div>
                                <div v-else-if="header.value === TABLEFIELDMAPPING.id">
                                    <!--Table id column link-->
                                    <router-link
                                        :class="isRowExpanded(item) ? 'table-row-open' : 'table-row-close'"
                                        @click.native="saveSelectedItem(item)"
                                        :to="buildRoute(
                                            item[tableConfiguration.tableSettings.rowKey],
                                            VUEROUTES.cluster,
                                            item[TABLEFIELDMAPPING.category] ? item[TABLEFIELDMAPPING.category] : '',
                                            routeParameters)"
                                    >
                                        {{item[TABLEFIELDMAPPING.id]}}
                                    </router-link>
                                </div>
                                <div v-else-if="header.value === TABLEFIELDMAPPING.claimId">
                                    <!--Table id column link-->

                                    <router-link
                                        :class="isRowExpanded(item) ? 'table-row-open' : 'table-row-close'"
                                        @click.native="saveSelectedItem(item)"
                                        :to="buildRoute(
                                            item[tableConfiguration.tableSettings.rowKey],
                                            VUEROUTES.cluster,
                                            item[TABLEFIELDMAPPING.category] ? item[TABLEFIELDMAPPING.category] : '',
                                            routeParameters)"
                                    >
                                        {{item[TABLEFIELDMAPPING.claimId]}}
                                    </router-link>
                                    
                                </div>
                                <div v-else-if="header.value === TABLEFIELDMAPPING.prototypeId">
                                    <!--Table prototype column link-->
                                    <router-link
                                        :class="isRowExpanded(item) ? 'table-row-open' : 'table-row-close'"
                                        @click.native="saveSelectedItem(item)"
                                        :to="buildRoute(
                                            item[TABLEFIELDMAPPING.prototypeUri] ? item[TABLEFIELDMAPPING.prototypeUri] : '',
                                            VUEROUTES.element,
                                            item[TABLEFIELDMAPPING.category] ? item[TABLEFIELDMAPPING.category] : '',
                                            routeParameters)"
                                    >
                                        {{item[TABLEFIELDMAPPING.prototypeId] ? item[TABLEFIELDMAPPING.prototypeId] : ''}}
                                    </router-link>
                                </div>
                                <div v-else-if="header.value === TABLEFIELDMAPPING.types || header.value === TABLEFIELDMAPPING.affiliation || header.value === TABLEFIELDMAPPING.claimerList ||  header.value === TABLEFIELDMAPPING.semantics || header.value === TABLEFIELDMAPPING.variableList">
                                    <!--Table types, variableList, or any other simple array-->
                                    <div class="kb-description-wrapper">
                                        <span v-for="(value, vIndex) in item[header.value]" :key="vIndex">
                                           {{value}}
                                        </span>
                                    </div>
                                </div>
                                <div v-else-if="item[TABLEFIELDMAPPING.highlight] && getComparableHeaders().includes(header.value)" class="kb-description-wrapper">
                                    <!--If the highlight field exists, find all comparable headers and their values. Then highlight based on the 'colorText' and 'text' keys that were added to the value -->
                                    <div :ref="'compare_data_div' + header.value" v-for="(value, vIndex) in getRowValues(item, header.value)" :key="vIndex">
                                        <span v-if="value[TABLEFIELDMAPPING.colorText]" :class="value[TABLEFIELDMAPPING.colorText] ? 'compare-text-color' : ''">
                                            <span>{{value.text}}</span>
                                        </span>
                                        <span v-else>{{value.text}}</span>
                                    </div>
                                </div>
                                <div v-else-if="header.value === TABLEFIELDMAPPING.dates" class="table-date-div">
                                    <div v-for="(date, dIndex) in item[header.value]" :key="dIndex">
                                        {{date}}
                                    </div>
                                </div>
                                <div v-else-if="header.value === 'data-table-expand' && expansionItemExists(item)">
                                    <!--adds expand row icon to the datatable row-->
                                    <v-icon :class="setExpandedClass(isExpanded)" @click="expand(!isExpanded)">mdi-chevron-down</v-icon>
                                </div>
                                <div v-else>
                                    <!--data under all other column headers that were not listed above-->
                                    {{item[header.value]}}<span v-if="header.value === TABLEFIELDMAPPING.percentage && !isNaN(item[header.value])">%</span>
                                </div>
                            </div>
                        </td>
                    </tr>
            </template>
            <!--Table row expanded - nested table inside of the datatable row-->
            <template v-slot:expanded-item="{ item }" v-if="tableConfiguration.secondaryHeaders && tableConfiguration.secondaryHeaders.expanded">
                <!--Places a nested table inside of the expanded row-->
                <td :class="'align-top'" :set="entry = tableConfiguration.secondaryHeaders.expanded" :colspan="tableConfiguration.primaryHeaders.length"
                :style="claimsShowHideRows(item)">
                    <SimpleTable :headers="entry.headers" :route="VUEROUTES.claimDetails" :data="item[entry.field]"
                    ></SimpleTable>
                </td>
            </template>
            <template v-slot:footer v-if="!hideFooter">
                <!--If the footer is shown, the results per page dropdown and pagination can be added to the DataTable footer-->
                <div class="pagination-footer">
                    <div v-if="tableConfiguration.tableSettings.showResultsPerPage" class="pagination-footer-text">Results per page:
                            <SelectDropdown :options="itemsPerPageArray" :selectedOption="paging ? paging.itemsPerPage : null"
                                            @selectDropdownChange="updateItemsPerPage($event)"
                            >
                            </SelectDropdown>
                    </div>
                    <div v-else class="pagination-footer-text"></div><!--Placeholder for footer alignment-->
                    <v-pagination
                            class="pagination-component"
                            v-model="page"
                            :length="pageCount"
                            :value="selectedPage"
                            total-visible="10"
                            @input="updatePageSelection($event)"
                    ></v-pagination>
                    <div class="pagination-footer-text-end">{{paginationText}}
                        <InfoTooltip :message="tooltipMessage"></InfoTooltip>
                    </div>
                </div>
            </template>
        </v-data-table>
        </div>
    </div>
</template>

<script> 
    import SelectDropdown from '@/components/SelectDropdown';
    import SimpleTable from '@/components/SimpleTable';
    import InfoTooltip from '@/components/InfoTooltip';
    import DataDisplayMixin from '@/mixins/DataDisplayMixin';
    import {mapMutations} from 'vuex';
    import {
        DATATYPES,
        VUEROUTES,
        TABLEFIELDMAPPING,
        LOADINGMESSAGE,
        NODATAMESSAGE,
        buildRoute
    } from '../utils';

    export default {
        name: 'DataTable',
        components: {SelectDropdown, SimpleTable, InfoTooltip},
        mixins: [DataDisplayMixin],
        props: {
            tableConfiguration: Object,
            statusMessage: String,
            routeParameters: {
                type: Array,
                required: false
            },
            hideData: {
                type: Boolean,
                default: false,
                required: false
            }
        },
        data: () => ({
            expanded: [],
            singleExpand: false,
            tooltipMessage: 'Data limit 500',
            expandedClass: 'v-icon notranslate v-data-table__expand-icon v-icon--link mdi mdi-chevron-down theme--light',
            hiddenHeaders: [],
            customSortBy: '',
            customSortDesc: false,
            hiddenFilters: new Set(),
        }),
        created() {
            this.VUEROUTES = VUEROUTES;
            this.TABLEFIELDMAPPING = TABLEFIELDMAPPING;
            this.LOADINGMESSAGE = LOADINGMESSAGE;
            this.NODATAMESSAGE = NODATAMESSAGE;
            this.DATATYPES = DATATYPES;
            this.buildRoute = buildRoute;
            this.customSortBy = this.tableConfiguration.tableSettings.defaultSortBy ?
                this.tableConfiguration.tableSettings.defaultSortBy : 'headline';
            this.customSortDesc = this.tableConfiguration.tableSettings.defaultSortDesc ?
                this.tableConfiguration.tableSettings.defaultSortDesc : false;
        },
        methods: {
            ...mapMutations(['updateDataSelection']),
            showColumn(headerValue) {
                let newArr = [];
                for(let column of this.hiddenHeaders) {
                    if (column !== headerValue) {
                        newArr.push(column)
                    }
                }
                this.hiddenHeaders = newArr;
            },
            hideColumn(headerValue) {
                this.hiddenHeaders.push(headerValue);
            },
            resetColumn() {
                this.hiddenHeaders = [];
            },
            updateCustomSort (headerValue, isDesc) {
                this.customSortBy = headerValue;
                this.customSortDesc = !isDesc;
            },
            sortIconClass(headerValue) {
                if (headerValue === this.customSortBy)
                    return this.customSortDesc ? 'arrow-icon-up' : 'arrow-icon-down';
                else
                    return 'arrow-icon-up light-icon-button'
            },
            sortUpdate(data, index, sortDesc) {
                this.customSort(data, [index], [sortDesc]);
                this.updateCustomSort(index, sortDesc);
            },
            customSort(items, index, isDesc) {
                items.sort((a, b) => {
                    //if row highlight is active and comparableHeaders exist for this index
                    if (a.hasOwnProperty(TABLEFIELDMAPPING.highlight) && a[TABLEFIELDMAPPING.highlight]
                        && this.getComparableHeaders().length > 0 && this.getComparableHeaders().includes(index[0])) {
                        //sorts object text values or regular string values
                        let aValues = this.getRowValues(a, index[0]);
                        let bValues = this.getRowValues(b, index[0]);

                        aValues = aValues === null ? '' : aValues;
                        bValues = bValues === null ? '' : bValues;

                        aValues = typeof aValues[0] === 'object' ? [...aValues.map(av => av.text)].join(',').toLowerCase() : aValues.join(',').toLowerCase();
                        bValues = typeof bValues[0] === 'object' ? [...bValues.map(av => av.text)].join(',').toLowerCase() : bValues.join(',').toLowerCase();

                        if (!isDesc[0]) {
                            return aValues && bValues ?
                                aValues.localeCompare(bValues) : aValues ? 1 : -1;
                        } else {
                            return aValues && bValues ?
                                bValues.localeCompare(aValues) : aValues ? -1 : 1;
                        }
                    }
                    //if value is a number
                    else if (typeof a[index] === 'number' || typeof b[index] === 'number') {
                        return !isDesc[0] ? a[index] - b[index] : b[index] - a[index];
                    }
                    //if value contains format of 'title: number'
                    else if ((typeof a[index] === 'string' && a[index].match('\\w+:\\s[0-9]+')) || (typeof b[index] === 'string' && b[index].match('\\w+:\\s[0-9]+'))) {
                        const aValue = a[index].split(' ')[1];
                        const bValue = b[index].split(' ')[1];

                        return !isDesc[0] ? aValue - bValue : bValue - aValue;
                    }
                    //if value is a string or array
                    else if (typeof a[index] !== 'undefined') {
                        a[index] = a[index] === null ? '' : a[index];
                        b[index] = b[index] === null ? '' : b[index];

                        const aValue = Array.isArray(a[index]) ? a[index].join(',').toLowerCase() : a[index].toLowerCase();
                        const bValue = Array.isArray(b[index]) ? b[index].join(',').toLowerCase() : b[index].toLowerCase();
                        return !isDesc[0] ? aValue.localeCompare(bValue.toLowerCase()) : bValue.toLowerCase().localeCompare(aValue.toLowerCase());
                    }
                });

                return items;
            },
            isRowExpanded(row) {
                let isExpanded = false;
                for (let i = 0; i < this.expanded.length; i++) {
                    isExpanded = this.expanded[i][this.tableConfiguration.tableSettings.rowKey] === row[this.tableConfiguration.tableSettings.rowKey];
                    if(isExpanded) {
                        break;
                    }
                }
                return isExpanded;
            },
            setItemClass(item) {
                return item.hasOwnProperty(TABLEFIELDMAPPING.highlight) && item[TABLEFIELDMAPPING.highlight] ? 'highlight-added' : '';
            },
            turnHighlightOn(item, toggle) {
                let highlight = !item.hasOwnProperty(TABLEFIELDMAPPING.compare) && toggle || item[TABLEFIELDMAPPING.compare] && item[TABLEFIELDMAPPING.highlight];
                return highlight;
            },
            updateOnClick(item) {
                if(item[TABLEFIELDMAPPING.highlight]) {
                    //emit event back to the DataTable parent file in order to:
                    //Set the TABLEFIELDMAPPING.highlight to true if it is not already
                    //Add the TABLEFIELDMAPPING.compare field to the item(row) provided and set to true
                    //Also add a 'colorText' and 'text' key to the item data that needs to be highlighted when compared.
                    // The 'colorText' value should always equal true or false, while the 'text' value should equal the data value.
                    this.$emit('activateCompare', {item: item, type:this.tableConfiguration.tableSettings.tableHeading});
                    this.saveSelectedItem(item);
                }
                else {
                    this.$emit('updateItem', item);
                }
            },
            saveSelectedItem(data) {
                    this.updateDataSelection(data);
            },
            setExpandedClass(isExpanded) {
                return isExpanded ? this.expandedClass + ' v-data-table__expand-icon--active' : this.expandedClass;
            },
            statementHeaderExists() {
                return !!this.tableConfiguration.primaryHeaders.find(h => h.value === TABLEFIELDMAPPING.statement);
            },
            getComparableHeaders() {
                const indexStart = this.statementHeaderExists ? 2 : 1;
                return this.tableConfiguration.primaryHeaders.slice(indexStart, -1).map(header => header.value);
            },
            getRowValues(item, headerValue) {
                let argumentsByRole = [];
                //all results should have an object with key-value pairs where 'text' is a the key and the value is the value for the specified item.
                //If the value is to be highlighted, the 'colorText' key with a value of true should be added to the object in order to highlight the value for the comparison feature
                if(headerValue === TABLEFIELDMAPPING.dates) {
                    for (const date of item[TABLEFIELDMAPPING.dates]) {
                        if(date.hasOwnProperty(TABLEFIELDMAPPING.colorText)) {
                            //if the 'colorText' key exists then the 'text' key should also exist
                            argumentsByRole.push(date);
                        }
                        else {
                            //If there is no 'colorText' the date value has not been changed and the raw date value gets added with a 'text' key
                            argumentsByRole.push({text:date});
                        }
                    }
                }
                else {
                    argumentsByRole.push({text:item[headerValue]});
                }
                return argumentsByRole;
            },
            expansionItemExists(item) {
                let itemExists = false;
                const expandConfig = this.tableConfiguration.secondaryHeaders.expanded;
                if(this.tableConfiguration.secondaryHeaders && expandConfig) {
                    const eItem = item[expandConfig.prototype] ? item[expandConfig.prototype][expandConfig.field] : item[expandConfig.field];
                    itemExists = eItem && eItem.length > 0;
                }
                return itemExists;
            },
            claimsGetFilterData(data) {
                let claimRoles = new Set();
                if (data.length > 0) {
                    for (const z of data) {
                        this.claimsGetItemRoles(z).forEach(claimRoles.add, claimRoles);
                    }
                }
                return [...claimRoles];
            },
            claimsSetFilterClass(filter) {
                return (this.hiddenFilters.has(filter)) ? 'filter-pill-on' : 'filter-pill-off';
            },
            claimsSetHideRowsList(filter) {
                if (this.hiddenFilters.has(filter)) {
                    this.hiddenFilters.delete(filter);
                } else {
                    this.hiddenFilters.add(filter);
                }
                this.hiddenFilters = new Set(this.hiddenFilters);
            },
            claimsShowHideRows(item) {
                let hideRow = false;
                let itemRoles = new Set();
                if (this.hiddenFilters.size > 0) {
                    //TODO: The claimsGetItemRoles function is specific to role filters, but this section can be refactored in the future to work for whatever filter is needed
                    itemRoles = this.claimsGetItemRoles(item);
                    if ([...this.hiddenFilters].every(elem => itemRoles.has(elem))) {
                        hideRow = true;
                    }
                } else {
                    hideRow = true;
                }
                return hideRow ? '' : 'display:none;';
            },
            claimsGetItemRoles(item) {
                let itemRoles = new Set();
                for (const entity of item['entities']) {
                    for (const role of entity['roles']) {
                        itemRoles.add(role);
                    }
                }
                return itemRoles;
            }
        }
    }
</script>

<style lang="scss">
    .align-top {
        vertical-align: top !important;
        div {
            padding: 10px;
        }
    }

    .info-text-wrapper {
        @include flex-row-nowrap;
        min-width: 0;
        div {
            width: 480px;
        }
    }

    .table-row-operation {
        @extend .row-heading;
        @include no-text-decoration;
        @extend .page-link;
    }

    .table-row-close {
        font-size: 15px;
        font-weight: bold;
        text-decoration: none;
        color: $theme-teal !important;
    }

    .table-row-open {
        @extend .table-row-operation;
        color: $theme-dark-offset !important;
    }

    .pipe-spacing {
        margin: 0 6px;
    }

    .highlight-added{
        text-decoration: none !important;
        background-color: #aceed8a6 !important;
    }

    .space-description-left {
        font-size: 13px;
        margin-left: 6px;
        color: $theme-break-line-color;
    }

    .compare-text-color {
       color: #04a7b2 !important;
    }

    .space-div {
        width: 24px;
        height: 24px;
    }

    /************************************************************/
    /*hideable columns styling*/
    /************************************************************/

    .filter-pill-on {
        background-color: $background-pale-gold;
        border: 2px #fdd195 solid;
        color: $theme-primary-color;
        padding: 1px 1px 1px 6px;
        text-decoration: none;
        margin: 4px;
        cursor: pointer;
        border-radius: 16px;
        @include flex-row-nowrap;
        align-items: center;
        height: 25px;
    }

    .filter-pill-off {
        background-color: white;
        border: 2px #b1b5b5 solid;
        color: $theme-primary-color;
        padding: 1px 10px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 16px;
        min-height: 30px;
    }

    .hideable-column-header {
    @include flex-row-nowrap;
    align-items: center;
    }

    .arrow-icon-down {
        transform: rotate(180deg);
        transition: all .3s ease;
        color: white !important;
        font-size: 20px !important;
    }

    .arrow-icon-up {
        transform: rotate(0deg);
        transition: all .3s ease;
        color: white !important;
        font-size: 20px !important;
    }

    .hide-icon {
        color: white !important;
        font-size: 20px !important;
    }

    .show-icon {
        color: $theme-primary-color !important;
        font-size: 20px !important;
    }

    .table-header-icon-selected {
        color: #424040 !important;
        padding: 0;
        font-size: 30px;
    }

    .table-date-div {
        width: 180px;
    }

</style>
