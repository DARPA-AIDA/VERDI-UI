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
 - The following vue component is designed to be the filter layout for all pages in
 - the UI. This is a very useful component when different filters are used per page.
 - Many filter controllers such as the ClaimFilterController can be designed and then
 - imported into this file. The filterRoute can be used to determine which controller
 - to apply. The Filter Menu can be found on the Claims List page in the UI located at
 - http://localhost:8081/#/claims.
 -->

<template>
    <form id="filter_menu" ref="filter_menu" class="filter-menu" @submit.prevent="submitFilters()" method="get">
        <div class="filter-menu-content">
            <div class="filter-menu-header">
                <div class="primary-heading">Filters</div>
                <div>
                    <button id="delete_all_button" class="dark-icon-button" @click.prevent="resetFilterForm()">
                        <v-icon>mdi-refresh</v-icon>
                    </button>
                </div>
            </div>
            <div class="filter-menu-filters">
                <ClaimFilterController v-if="filterRoute === VUEROUTES.claims" ref="claim_filter_controller"></ClaimFilterController>
            </div>
            <div class="filter-submit-button-wrapper">
                <button id="submit_button" type="submit" class="dark-apply-button">
                    Apply Filter
                </button>
            </div>
        </div>
    </form>
</template>

<script>

    import ClaimFilterController from '@/components/ClaimFilterController';
    import RouteParametersMixin from '@/mixins/RouteParametersMixin';
    import {PARAMETERNAMES, VUEROUTES} from '../utils';
    import {mapMutations, mapGetters} from 'vuex';

    export default {
        name: 'FilterMenu',
        mixins: [RouteParametersMixin],
        data: () => ({
            showSaveOptions: false,
            showDateTooltip: false
        }),
        components: {ClaimFilterController},
        created() {
            this.VUEROUTES = VUEROUTES;
        },
        computed: {
            ...mapGetters(['getFilterKeys']),
            filterRoute() {
                return this.$route.name;
            }
        },
        methods: {
            ...mapMutations(['updateSavedFilters', 'updateFilterReset', 'updateFilterSubmission']),
            resetFilterForm() {
                this.updateFilterReset(true);
            },
            submitFilters() {
                //Only add/update the filterId and savedFilters if valid filters exist
                //console.warn(this.getFilterKeys);

                if (this.getFilterKeys.length > 0) {
                    let timestamp = new Date();
                    this.updateRouteParams(PARAMETERNAMES.filter, timestamp.getTime());
                    this.updateSavedFilters({id: timestamp.getTime()});
                } else {
                    this.updateRouteParams(PARAMETERNAMES.filter);
                }
                this.updateFilterSubmission(true);
                //console.warn('updateFilterSubmission ');
            }
        }
    }
</script>

<style lang="scss" scoped>
    .filter-menu {
        position: relative;
        background-color: white;
        z-index: 2;
        box-shadow: $heavyweight-box-shadow;
        height: 100%;
        width: 380px;
        min-height: $full-page-min-height;
    }

    .filter-menu-content {
        background-color: #ffffff;
        max-height: 100%;
        min-height: 238px;
        display: flex;
        flex-direction: column;
        position: relative;
    }

    .filter-menu-header {
        padding: 20px 8px 0;
        @include flex-row-nowrap;
        color: $theme-primary-color;
        justify-content: space-between;
        z-index: 4;
        border-bottom: 1px solid $theme-break-line-color;
        height: $filter-menu-header-height;
        align-items: center;

        h4{
            flex: 1 1 auto;
            align-self: center;
        }
    }

    .filter-menu-filters {
        @include flex-column-nowrap;
        min-height: 115px;
        padding: 8px 0;
        max-height: 100%;
        clear: both;
    }

    .filter-submit-button-wrapper{
        width: 100%;
        display: flex;
        align-content: center;
        justify-content: center;
        position: relative;
        margin: 14px 0 14px;

        .dark-apply-button {
            width: 90%;
            margin: auto;
            @extend .dark-teal-submit-button
        }
    }

    .filter-list-header-wrapper{
        @include top-bottom-margin;
        display: flex;
        justify-content: space-between;
        border-bottom: 0 none;
        box-shadow: $middleweight-box-shadow;

        .filter-list-header{
            @include flex-column-nowrap;
            padding: 6px;
        }
    }
</style>
