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
 - The following vue component is a transition menu that is used to list additional menu options
 - in the top navigation bar of the UI. It is currently being used in the App.vue file. This
 - component can be found in the top-right corner of the UI when clicking on the "vertical dots"
 - icon located at http://localhost:8081/#/
 -->

<template>
    <transition :name="menuData.transitionType">
        <div :class="menuData.menuClasses" >
            <div v-for="(option, key ) in menuData.menuOptions" :key="key" @click="$emit('transitionMenuOptionSelected', option.path)">
                <router-link v-if="option.active && option.type === 'internal'" :to="'/' + option.path"><v-icon>{{option.icon}}</v-icon>{{option.value}}</router-link>
                <span v-else-if="option.active && option.type === 'external'" @click="openExternalLink(option.path)"><v-icon class="button-icon">{{option.icon}}</v-icon>{{option.value}}</span>
                <span v-else><v-icon>{{option.icon}}</v-icon>{{option.value}}</span>
            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        name: 'TransitionMenu',
        props: {
            menuData : Object
        },
        methods: {
            openExternalLink (path) {
                window.open(path, '_blank');
            },
        }
    }
</script>

