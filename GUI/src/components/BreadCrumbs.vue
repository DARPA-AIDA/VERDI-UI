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
 - The following vue component was designed to display breadcrumbs to allow the user
 - to keep track of where they are on the site and navigate to past pages. It has
 - not been fully implemented but displays an example when added to the
 - Secondary Navigation bar in the App.vue file. The mock-up example is the Analyze
 - link beside the Filter Menu located at https://vzv88e.axshare.com/#id=kl5itx&p=claim_frame&g=1
 -->

<template>
    <div>
        <ul class="breadcrumb">
            <li v-for="(crumb, key, i) in crumbs" :key="key" >
                <span v-if="clickable"><a :id="key" @click.prevent="$emit('crumbSelection', $event.target.id)" :class="crumb.disabled ? 'breadcrumb-disabled' : ''">{{ crumb.text }}</a></span>
                <span v-else :id="key" :class="crumb.disabled ? 'breadcrumb-disabled' : ''">{{ crumb.text }}</span>
                <v-icon v-show="i < Object.keys(crumbs).length - 1" class="breadcrumb-arrow">forward</v-icon>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: 'BreadCrumbs',
        props: {
            crumbs: Object,
            clickable: {
                type: Boolean,
                default: true
            }
        }
    };
</script>

<style lang="scss" scoped>
    /* Unstyle the unordered list */
    .breadcrumb {
       @include flex-row-nowrap;
        list-style: none;
        padding-left: 0;
    }

    /* Display list items side by side */
    ul.breadcrumb li {
        display: inline
    }

    ul.breadcrumb li span {
        color: $theme-teal-text;
        @include no-text-decoration;
    }

    /* Add an underline on mouse-over for all, but the last link */
    ul.breadcrumb li span a {
        @extend .page-link;
    }

    /* Add disabled styling to the last link */
    .breadcrumb-disabled, .breadcrumb-disabled:hover {
        color: $theme-primary-color !important;
        cursor: unset;
    }

    .breadcrumb-arrow{
        font-size: 18px !important;
        padding: 0 8px;
    }
</style>
