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
 - The following vue component is a basic select dropdown used for making a single
 - from a list of items. The mock-up example for this component
 - is on the Claims List page of the UI located at http://localhost:8081/#/claims
 -->
<template>
    <select
            :class="assignClass()"
            :required="required"
            :disabled="disabled"
            :style= "{ 'background-image': 'url(' + arrow + ')' }"
            @change="$emit('selectDropdownChange', $event)"
    >
        <option v-for="(option, index) in options"
                :key="index"
                :selected="option.toString() === selectedOption"
                :value="required && index === 0 ? '' : option">{{option}}
        </option>
    </select>
</template>

<script>
    import arrow from '@/assets/images/select-arrow.svg';
    import { SELECTDISPLAY } from '../utils';

    export default {
        name: 'SelectDropdown',
        data:() => ({
            arrow
        }),
        props: {
            options: Array,
            selectedOption: String,
            selectDisplay: String,
            required: Boolean,
            disabled: {
                type: Boolean,
                default: false
            }
        },
        methods: {
            assignClass() {
                return this.selectDisplay === SELECTDISPLAY.button ? 'select-css display-with-borders' :
                        this.selectDisplay === SELECTDISPLAY.text ? 'select-css display-no-decoration' :
                        this.selectDisplay === SELECTDISPLAY.input ? 'select-css display-as-input' :
                        'select-css display-as-underlined';
            }
        }
    }
</script>

<style lang="scss" scoped>
    select option:checked {
        background-color: $theme-dark-light-color !important;
    }

    .select-css {
        display: block;
        color: $theme-primary-color;
        line-height: 1.3;
        appearance: none;
        background-repeat: no-repeat, repeat;
        background-position: right .7em top 50%, 0 0;
        background-size: .65em auto, 100%;
        cursor: pointer;
    }

    .select-css:focus {
        border-color: #aaa;
        color: #222;
        outline: none;
    }

    /* Disabled styles */
    .select-css:disabled, .select-css[aria-disabled=true] {
        color: graytext;
        background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22graytext%22%20d%3D%22M287%2069.4a17.6%2017.6%200%200%200-13-5.4H18.4c-5%200-9.3%201.8-12.9%205.4A17.6%2017.6%200%200%200%200%2082.2c0%205%201.8%209.3%205.4%2012.9l128%20127.9c3.6%203.6%207.8%205.4%2012.8%205.4s9.2-1.8%2012.8-5.4L287%2095c3.5-3.5%205.4-7.8%205.4-12.8%200-5-1.9-9.2-5.5-12.8z%22%2F%3E%3C%2Fsvg%3E'),
        linear-gradient(to bottom, #ffffff 0%,#e5e5e5 100%);
    }
    .select-css:disabled:hover, .select-css[aria-disabled=true] {
        border-color: #aaa;
    }
</style>

