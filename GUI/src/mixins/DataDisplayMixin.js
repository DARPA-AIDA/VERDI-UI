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

import { upperFirstLetter, removePrefix, PREFIXTYPES, TABLESELECTIONS, TABLEFIELDMAPPING} from '../utils'
import {mapActions} from 'vuex';

export default {
    props: {
        data: Array,
        sort: {
            type: Object,
            required: false,
            value: {[TABLESELECTIONS.sortBy]: TABLEFIELDMAPPING.headline, [TABLESELECTIONS.sortDesc]: false}
        },
        loading: Boolean,
        paging: Object,
        hideHeader: Boolean,
        hideFooter: Boolean
    },
    data: () => ({
        itemsPerPageArray: [10, 20, 50, 100, 'All'],
        page: 1,
        pageStart: 0,
        numOfPages: 0
    }),
    computed: {
        itemsPerPage: function () {
            return this.paging && this.paging[TABLESELECTIONS.itemsPerPage] && this.data ?
                (this.paging[TABLESELECTIONS.itemsPerPage] === 'All' ? this.data.length : parseInt(this.paging[TABLESELECTIONS.itemsPerPage])) :
                this.itemsPerPageArray[0];
        },
        paginationText: function () {
            let text = '';
            if(this.data && this.itemsPerPage < this.data.length) {
                text = (this.pageStart + 1) + '-' + this.pageStop + ' of ';
            }
            return text + (this.data && this.data.length || 0) + ' Results'
        },
        pageStop: function() {
            return this.pageStart + this.itemsPerPage;
        },
        pageCount: {
            get: function() {
                return this.data ? Math.ceil(this.data.length / this.itemsPerPage) : 0;
            },
            set: function(value) {
                this.numOfPages = value;
            }
        },
        selectedPage: function () {
            return this.paging && this.paging[TABLESELECTIONS.pageNumber] ? this.paging[TABLESELECTIONS.pageNumber] : this.page;
        }
    },
    methods: {
        ...mapActions(['updateTableSelections']),
        getFieldTitle(key) {
            if(key.includes(PREFIXTYPES.underscore)) {
                key = removePrefix(key, PREFIXTYPES.underscore)
            }
            return upperFirstLetter(key);
        },
        getAverageConfidence(confidence, count) {
            let average = confidence / count;
            return average.toPrecision(2);
        },
        updateHeaderPagination(event) {
            //Needed for updating the paginationText in the header table and the pagination bar in the footer of the table
            this.pageStart = event.pageStart;
            this.page = this.paging && this.paging[TABLESELECTIONS.pageNumber] ? this.paging[TABLESELECTIONS.pageNumber] : event.page;
            this.pageCount = event.pageCount ? event.pageCount : this.numOfPages;
        },
        updatePageSelection(event) {
            this.updateTableSelections({option: TABLESELECTIONS.pageStart, value: event.pageStart ? event.pageStart : this.pageStart});
            this.updateTableSelections({option: TABLESELECTIONS.pageNumber, value: event.page ? event.page : this.page});
        },
        updateItemsPerPage(selection) {
            this.updateTableSelections({option: TABLESELECTIONS.itemsPerPage, value: selection.target.value});
        },
        updateSortBy(field) {
            this.updateTableSelections({option: TABLESELECTIONS.sortBy, value: field});
        },
        updateSortDesc(value) {
            this.updateTableSelections({option: TABLESELECTIONS.sortDesc, value: value})
        }
    }
};

