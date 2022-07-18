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
 - The following vue file was designed as a grid display for documents with image icons
 - if available, but can be modified as a grid view for any data with image icons.
 - The mock-up example can be found at the bottom of the Event Details page located at
 - https://f2p9s0.axshare.com/#id=pmgwq8&p=event_details__dev_&g=1
 -->

<template>
    <div class="section-space">
        <div class="secondary-heading">Documents ({{data && data.length ? data.length : 0}})</div>
        <div class="document-grid-container grid-scroll-display">
            <div v-for="(item, index) in data" :key="index" class="grid-item">
                <span>
                    <template>
                        <v-tooltip bottom :nudge-top="10">
                            <template v-slot:activator="{ on }">
                                <router-link
                                    class="table-row-close"
                                    :to="buildRoute(item.id, VUEROUTES.doc)"
                                    target="_blank"
                                    >
                                    <span v-on="on">{{ item.id }}</span>
                                </router-link>
                            </template>
                            <span>{{ item.title }}</span>
                        </v-tooltip>
                    </template>
                </span>
                <span class="grid-icon-wrapper"><v-icon class="grid-v-icon">mdi-file-document-outline</v-icon></span>
            </div>
        </div>
    </div>
</template>

<script>
//TODO: Add title as tooltip to document link
import { buildRoute, VUEROUTES } from '../utils'
    export default {
        name: 'DocumentGrid',
        props: {
            data: Array
        },
        created() {
            this.buildRoute = buildRoute;
            this.VUEROUTES = VUEROUTES;
        }
    }
</script>

<style lang="scss" scoped>
    .document-grid-container {
        display: grid;
        grid-template-columns: 25% 25% 25% 25%;
        margin-top: 10px;
    }
    .document-grid-container .grid-item {
        @include flex-row-nowrap;
        border: thin solid rgba(0, 0, 0, 0.12);
        align-items: center;
        padding-left: 10px;
        justify-content: space-between;
    }
    .grid-icon-wrapper{
        border: thin solid rgba(0, 0, 0, 0.12);
        padding: 6px 8px;
    }

    .grid-v-icon {
        font-size: 30px;
    }
</style>

