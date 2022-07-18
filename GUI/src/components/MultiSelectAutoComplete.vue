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
 - The following vue component is similar to the AutoComplete component but offers
 - additional features such as multi-select, select all, and data selections being
 - displayed as individual chips(pills). Data selections can also be displayed as
 - a single chip along with a count for any additional selections. The component is
 - located in the UI on the Filter Menu of the Claims List page at http://localhost:8081/#/claims.
 -->

<template>
    <div>
        <v-autocomplete
                :ref="'multi_select_autocomplete' + index"
                v-model="selections"
                :disabled=disabled
                :required=required
                :loading=false
                multiple
                :no-data-text="message"
                :items="items"
                :label=placeholder
                @click:clear="$emit('autoCompleteClear', $event.target.value)"
                :change="submitInputChange()"
        >
            <template v-slot:prepend-item>
                <v-list-item
                        ripple
                        @click="closeFilter"
                >
                    <v-list-item-content text-align='right'>
                        <v-list-item-title>
                            Close
                        </v-list-item-title>
                    </v-list-item-content>                    
                </v-list-item>   

                <v-list-item
                        ripple
                        @click="toggle"
                >
                    <v-list-item-action>
                        <v-icon :color="selections.length > 0 ? 'indigo darken-4' : ''">
                            {{ icon }}
                        </v-icon>
                    </v-list-item-action>
                    <v-list-item-content text-align='left'>
                        <v-list-item-title>
                            <span @click="toggle">Select All</span>
                        </v-list-item-title>
                    </v-list-item-content>
                    <v-list-item-icon @click="closeFilter">
                        <v-icon class="list-item-close-icon">mdi-close</v-icon>
                    </v-list-item-icon>
                </v-list-item>
             
                <hr class="hr-light">
            </template>
            <template v-if="!showAllChips" v-slot:selection="{ item, index }">
                <v-chip v-if="index === 0" :class="selections.length > 1 ? 'multi-select-chip-with-caption' : 'multi-select-chip'"
                    :value="item.value"
                        >
                    <span>{{ item.text }}</span>
                </v-chip>
                <span v-if="index === 1" class="text-caption multi-select-caption">
                    (+ more)
                 </span>
            </template>
        </v-autocomplete>
        <ValidationTooltip :icon="'mdi-alert-box'" :message="errorMessage" :displayClass="'autocomplete'" :refId="index" :showTooltip="validate" @updateShowTooltip="updateShowTooltip($event)"></ValidationTooltip>
    </div>
</template>

<script>
    import RouteParametersMixin from '@/mixins/RouteParametersMixin';
    import {NODATAMESSAGE, arraysMatch} from '../utils';
    import ValidationTooltip from '@/components/ValidationTooltip';

    export default {
        name: 'MultiSelectAutoComplete',
        mixins: [RouteParametersMixin],
        components: {ValidationTooltip},
        props: {
            items: Array,
            index: {
               type:Number,
                default: 0,
               required: false
            },
            required: Boolean,
            disabled: {
                type: Boolean,
                default: false
            },
            placeholder: String,
            enterKeySubmit: {
                type: Boolean,
                default: false
            },
            message: {
                type: String,
                default: NODATAMESSAGE,
                required: false
            },
            showAllChips: {
                type: Boolean,
                default: true,
                required:false
            }
        },
        data: () => ({
            selections: [],
            prevSelections: [],
            requestToValidate: false,
            errorNote : '',
            allSelected: false,
            someSelected: false,
        }),
        computed: {
            errorMessage() {
                return this.errorNote;
            },
            // allSelected() {
            //     return this.selections.length === this.items.length;
            // },
            // someSelected() {
            //     return this.selections.length > 0 && !this.allSelected;
            // },
            icon() {
                return this.allSelected ? 'mdi-close-box' : this.someSelected ? 'mdi-minus-box' : 'mdi-checkbox-blank-outline';
            },
            validate() {
                return this.requestToValidate;
            }
        },
        methods : {
            updateShowTooltip(value) {
                this.requestToValidate = value;
            },
            updateCount() {
                let count = this.selections.length - 1;
                if(this.items.length < this.selections.length) {
                    count = this.items.length - 1;
                }
                return count;
            },
            submitInputChange() {
                //if selection is made using the Select All option, emit the submission event
                this.$nextTick(() => {
                
                   // console.warn(this.prevSelections)
                    if(this.selections.length > 0) {
                        //If no items were passed in reset the selections
                        if(this.items.length === 0) {
                            this.selections = []; 
                        } //if selections have changed, emit updated selections
                        else if(!arraysMatch(this.prevSelections, this.selections)) {
                            this.prevSelections = this.selections;
                            this.$emit('autoCompleteSubmission', this.selections);
                        }
                    } //if previous selections have been deleted, emit no selections
                    else if(!arraysMatch(this.prevSelections, this.selections)) {
                        this.prevSelections = [];
                        this.selections = [];
                        this.$emit('autoCompleteSubmission', []);
                    }

                });
            },
            toggle () {
                //Set the selected items based on the Select All action
                this.$nextTick(() => {
                    const autocomplete = this.$refs['multi_select_autocomplete' + this.index];

                    // console.warn(this.selections.length);
                    // console.warn(autocomplete.value.length);

                    if (this.allSelected) {
                        this.selections = [];
                        this.allSelected = false;
                    }
                    // else if(autocomplete.filteredItems.length >= 50) {
                    //     this.errorNote = autocomplete.filteredItems.length + ' filters were submitted. Please limit to 50 filters.';
                    //     this.requestToValidate = true;
                    // }
                    else {
                        //this.selections = autocomplete.filteredItems;
                        this.selections =  [...new Set(autocomplete.filteredItems.map(d => d.value))]
                        this.allSelected = true;
                    }
                    
                    this.someSelected =  this.selections.length > 0 && !this.allSelected;

                });
            },
            closeFilter () {

                this.$nextTick(() => {
                    const autocomplete = this.$refs['multi_select_autocomplete' + this.index];
                    autocomplete.blur();
                });
            }

        }
    }
</script>
<style lang="scss" scoped>
    .multi-select-chip {
        max-width:100%;
    }

    .multi-select-chip-with-caption {
        max-width:78%;
    }

    .multi-select-caption {
        color: $theme-primary-color;
    }
    .list-item-close-icon {
        color:$theme-teal !important;
    }
</style>
