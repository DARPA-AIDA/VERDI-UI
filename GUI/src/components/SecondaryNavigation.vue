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
 - The following vue component was created as a second layer of navigation options
 - displayed under the main navigation in the header of the UI. It was meant to house
 - the Filter Menu and Breadcrumbs for the UI. This component can be found on the
 - Claims List page of the UI located at http://localhost:8081/#/claims
 -->

<template>
    <div class="secondary-navigation-container">
        <div class="secondary-navigation">
            <div class="secondary-navigation-content">
                <div id="filter_button_wrapper">
                    <button id="filter_button" class="dark-app-button" @click="$emit('updateShowFilter', !showFilter)">
                        <v-icon class="button-icon">mdi-filter-outline</v-icon>
                        Filter
                    </button>
                </div>
<!-- TODO: Implement breadcrumbs - Temporarily hiding breadcrumbs per UX team request
                <BreadCrumbs :crumbs="crumbs"></BreadCrumbs>
              -->
                <div></div>
            </div>
        </div>
    </div>
</template>

<script>
    //import BreadCrumbs from '@/components/BreadCrumbs';

    export default {
        name: 'SecondaryNavigation',
       // components: {BreadCrumbs},
        props: {
            showFilter: Boolean
        },
        data: () => ({
            crumbs: [
                {
                    text: 'Link',
                    disabled: false
                },
                {
                    text: 'Link 2',
                    disabled: false
                },
                {
                    text: 'You Are Here',
                    disabled: true
                }
            ],
            showAppFilter: null
        })
    };

</script>

<style lang="scss" scoped>
    .secondary-navigation-container {
        width: 100%;
        top: $content-below-filter;
    }

    .secondary-navigation {
        padding: 10px;
        background-color: $theme-dark-light-color;
        width: 100%;
        position: fixed;
        z-index: 3;
        box-shadow: $middleweight-box-shadow;
    }

    .secondary-navigation-content {
        align-items: center;
        @include flex-row-nowrap;

        .dark-app-button {
            background-color: $theme-teal;
        }
    }
</style>
