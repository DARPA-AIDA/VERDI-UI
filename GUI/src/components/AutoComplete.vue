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
 - The following vue component offers a text autocomplete input box. It is used for
 - filtering options in a dropdown list to match the inputted text and making a
 - single selection in the dropdown list. The mock-up example is the topic filter
 - located at https://vzv88e.axshare.com/#id=ls04uh&p=discover&g=1
 -->

<template>
    <v-autocomplete
            v-model="selection"
            single-line
            :clearable="clearable"
            :disabled=disabled
            :required=required
            :loading=false
            :no-data-text="message"
            :items="modifiedItems"
            :placeholder=placeholder
            @keyup.enter="enterKeySubmit === true ? $emit('autoCompleteSubmission', $event.target.value) : false"
            @input="submitSelection($event)"
            @update:search-input="submitInputChange($event)"
            @click:clear="$emit('autoCompleteClear', $event.target.value)"
    ></v-autocomplete>
</template>

<script>
    import RouteParametersMixin from '@/mixins/RouteParametersMixin';
    import {NODATAMESSAGE} from '../utils';


    export default {
        name: 'AutoComplete',
        mixins: [RouteParametersMixin],
        props: {
            items: Array,
            required: Boolean,
            disabled: {
                type: Boolean,
                default: false
            },
            clearable: {
                type: Boolean,
                default: true
            },
            placeholder: String,
            enterKeySubmit: {
                type: Boolean,
                default: false
            },
            inputValue: {
                type: String,
                default: null,
                required: false
            },
            message: {
                type: String,
                default: NODATAMESSAGE,
                required: false
            }
        },
        data: () => ({
            currentSelection: null,
            previousSelection: null,
            previousInput: null
        }),
        computed: {
            modifiedItems() {

                //The inputValue has to exist in the items list before it can be used as a selection value. To ensure this, we
                // must prepend the inputValue to the items in order to set the autocomplete "selection" value index
                // since there is no way to track the index for a dynamic items list
                return this.inputValue && this.items ? [this.inputValue, ...this.items] : this.items;
            },
            selection: {
                get: function () {
                    //check if an inputValue has been provided. If so, return the inputValue as the selection.
                    //If not, return the current selection
                    return this.inputValue !== this.previousSelection ? this.inputValue : this.currentSelection;
                },
                set: function (v) {
                    this.currentSelection = v;
                    this.submitInputChange(v);
                }
            }
        },
        methods: {
            submitInputChange(input) {
                //When the input updates, the autocomplete options will also be updated
                if(input != this.previousInput) {
                    this.previousInput = input;
                    this.$emit('autoCompleteInputChange', input);
                }
            },
            submitSelection(input) {
                //a new item was selected in the list
                if(input === this.currentSelection && this.currentSelection !== this.previousSelection) {
                    this.$emit('autoCompleteSubmission', this.currentSelection);
                    this.previousSelection = this.currentSelection;
                }
            }
        }
    }
</script>

