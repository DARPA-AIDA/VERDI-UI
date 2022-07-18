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
 - The following vue component is a tab display that uses vue slots to input any component that needs
 - to be displayed inside the tabs. The mock-up example for the tabs is the tabbed Arguments/Hypothesis
 - data tables located at https://f2p9s0.axshare.com/#id=pmgwq8&p=event_details__dev_&g=1
 -->
<template>
    <div>
        <div class="tab">
            <span v-for="(title, index) in titles" :key="index">
                <button
                        ref="tabLinks"
                        :class="index===currentTabIndex ? 'active tab-link' : 'tab-link'"
                        @click="$emit('tabSelection', $event.target.innerText)"
                >{{title}}</button>
            </span>
        </div>
        <div class="tab-content">
            <div v-for="(title, index) in titles" :key="index">
                <slot v-if="currentTabIndex===index" :name="title"></slot>
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        name: 'Tabs',
        props: {
            titles: Array,
            activeTabTitle: String
        },
        computed: {
            currentTabIndex() {
                return this.activeTabTitle ? this.titles.findIndex((title) => title.includes(this.activeTabTitle)) : 0;
            }
        }
    }
</script>

<style  lang="scss" scoped>
    .tab {
        overflow: hidden;
        position:relative;
        bottom: -8px;
        box-shadow: $full-box-shadow;
        height: 40px;
        display: flex;
        align-items: center;
        width: fit-content;
        border-top-left-radius: 6px;
        border-top-right-radius: 6px;
        background-color: #ffffff;
    }

    .tab-link {
        background-color: #f0f0f0;
        float: left;
        outline: none;
        cursor: pointer;
        padding: 4px 16px;
        font-size: 17px;
        color: #0d838b;
        margin-top: -9px;
        height: 33px;
        border: #eaeaea solid 2px;
        border-top: none;
    }

    .tab-link:hover {
        background-color: #eaeaea;
    }

    .tab-link.active {
        background-color: white;
        font-weight: bold;
        border: none;
    }

    .tab-content {
        padding: 6px 12px;
        box-shadow: $lightweight-box-shadow;
        min-height: 140px;
        @include flex-column-nowrap;
        justify-content: center;
    }

    .deactivate-pointer > .tab .tab-link{
        cursor: default;
    }


</style>

