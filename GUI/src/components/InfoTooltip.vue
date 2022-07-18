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
 - The following vue component is a much more aesthetically pleasing version of the title
 - property used on HTML tags. The component is activated when hovering over the expand(^)
 - icon on the Claims List page in the UI at http://localhost:8081/#/claims
 -->

<template>
    <v-tooltip bottom :nudge-top="10">
        <template v-slot:activator="{ on, attrs }">
            <v-icon :class="'info-icon-' + iconStyle" v-bind="attrs" v-on="on">{{icon}}</v-icon>
        </template>
        <span>{{message}}</span>
    </v-tooltip>
</template>

<script>
    export default {
        name: 'InfoTooltip',
        props: {
            icon: {
                type: String,
                default: 'mdi-information-outline'
            },
            iconStyle: {
                type: String,
                default: 'dark'
            },
            message: String
        }
    }
</script>

<style lang="scss" scoped>
    .info-icon {
        font-size: 14px;
        padding-left: 2px;
    }

    .info-icon-light {
        @extend .info-icon;
        color: $theme-dark-light-color !important;

    }

    .info-icon-dark {
        @extend .info-icon;
        color: $theme-link-color !important;
    }

    .info-icon-dark-header {
        @extend .table-header-icon;
        color: $theme-link-color !important;
    }

    .info-icon-teal-large {
        @extend .table-header-icon;
        color: $theme-teal-text !important;
    }

</style>

