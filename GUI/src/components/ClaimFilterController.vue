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
  - Created by snordt on 12/7/21
-->
<!--
 - The following vue component was designed to display multiple MultiSelectAutoComplete
 - components within a single space. This controller is most useful for filters where the
 - same component is being used with different data inputs. An example of this form is
 - in the filter menu located at http://localhost:8081/#/claims
 -->

<template>
    <div class="full-width">
        <div v-for="(component, cIndex) in Object.keys(componentDisplay)" :key="component + cIndex">
            <div v-if="component === COMPONENTNAMES.multiSelect">
                <div v-for="(data, dIndex) in componentDisplay[component]" :key="dIndex" class="pad-horizontally">
                    <div class="multi-claim-autocomplete-input">
                        <MultiSelectAutoComplete class="multi-claim-filter-input"
                                                 :items="data.items"
                                                 :required="data.required"
                                                 :placeholder="data.placeholder"
                                                 :index="dIndex"
                                                 :message="'No ' + data.messageString +  ' available'"
                                                 :showAllChips=false
                                                 @autoCompleteSubmission="updateMultiSelect($event,component, data.id)"
                                                 @autoCompleteClear="updateMultiSelect($event, component, data.id)"
                        ></MultiSelectAutoComplete>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import MultiSelectAutoComplete from '@/components/MultiSelectAutoComplete';
    import { COMPONENTNAMES, DATATYPES, PARAMETERNAMES, arraysMatch } from '../utils';
    //import { COMPONENTNAMES, DATATYPES, PARAMETERNAMES } from '../utils';
    import API from '../api';
    import { mapActions, mapGetters, mapMutations, mapState } from 'vuex';
    import RouteParametersMixin from '@/mixins/RouteParametersMixin';

    class ComponentData {
        constructor(id, items, placeholder, messageString, required) {
            this.id = id;
            this.items = items;
            this.placeholder = placeholder;
            this.messageString = messageString;
            this.required = required;
        }
    }

    function initialState() {
        return {
            displayData: [], //MultiSelect : [{id:'', items:[], placeholder:'', messageString: '', required: ''}]
            responseData: [],
            filterTypes: []
        }
    }

    export default {
        name: 'ClaimFilterController',
        components: {MultiSelectAutoComplete},
        mixins: [RouteParametersMixin],
        data: () => (initialState()),
        created() {
            this.COMPONENTNAMES  = COMPONENTNAMES;
            this.DATATYPES = DATATYPES;
        },
        mounted() {
            this.resetQueryForm();
        },
        computed: {
            ...mapState(['filterReset', 'filters']),
            ...mapGetters(['getFilterByType', 'getFilterKeys']),
            componentDisplay() {
                if(this.filterReset) {
                    this.resetQueryForm();
                }
                return this.displayData;
            }
        },
        methods: {
            ...mapMutations(['updateFilterReset', 'updateFilterSubmission']),
            ...mapActions(['updateFilters', 'removeFilter']),
            updateMultiSelect(values, componentName, filterType) {
                //update displayData with selected values

                if(values.length > 0) {

                    let selectedData = [];

                    //find the data that matches the selected values
                    for (const value of values) {
                        selectedData = [...selectedData, ...this.responseData.filter(d => d[filterType] === value)];
                    }

                    for (const type of this.filterTypes) {

                        //This code allows for updating the options for all other select components when one component is updated

                            if(type !== filterType) {
                                let filterIndex = this.displayData[componentName].findIndex(d => d.id === type);
                                //create a deep clone of the displayData for this type and collect unique values from the selected data
                                let selectedObject = {...this.displayData[componentName].find(d => d.id === type)};
                                

                                //selectedObject.items = [...new Set(selectedData.map(d => d[type]))];
                                
                                //for (const testType of [DATATYPES.queryClaimId, DATATYPES.subtopic, DATATYPES.baseGraph]) {
                                for (const testType of [DATATYPES.queryClaimId]) {
                                    if(type !== filterType) {
                                        if (type == testType.id) {
                                            selectedObject.items =  selectedData.map(k => ({ text: k[testType.text], 
                                                                                            value: k[testType.id],
                                                                                            disabled: false}));
                                            break;
                                        }
                                    }
                                }

                                //If the component items do not match the selectedData items, update the component items with the selectedData values
                                if(!arraysMatch(this.displayData[componentName][filterIndex].items, selectedObject.items)) {
                                    this.displayData[componentName][filterIndex].items = selectedObject.items;
                                }

                                //the component items should all be valid based on the selectedData. If they are not update filters with only the valid component items
                                const typeFilterExists = this.getFilterKeys.find(k => k === type);
                                //if(typeFilterExists && type !== 'subtopic') {
                                if(typeFilterExists) {

                                    // console.warn('arraysMatch');
                                    // console.warn(type);
                                    // console.warn(arraysMatch(this.filters[type], [...new Set(selectedObject.items.map(d => d.value))]));

                                    //if(!arraysMatch(this.filters[type], selectedObject.items)) {
                                    if(!arraysMatch(this.filters[type], [...new Set(selectedObject.items.map(d => d.value))])) {

                                        const selectedObjectItemValues = [...new Set(selectedObject.items.map(d => d.value))]    

                                        //const diffValues = this.filters[type].filter(element => !selectedObject.items.includes(element));
                                        const diffValues = this.filters[type].filter(element => !selectedObjectItemValues.includes(element));

                                        // console.warn('this.filters[type]');
                                        // console.warn(this.filters[type]);

                                        // console.warn('selectedObjectItemValues');
                                        // console.warn(selectedObjectItemValues);


                                        // console.warn('diffValues');
                                        // console.warn(diffValues);



                                        if(diffValues.length === 0) {
                                            // this.updateFilters({type: type, filter: selectedObject.items});
                                            this.updateFilters({type: type, filter: [...new Set(selectedObject.items.map(d => d.value))]});
                                            // console.warn('A');
                                            // console.warn(this.updateFilters);
                                        }
                                        else {
                                            const validValues = this.filters[type].filter(element => !diffValues.includes(element));
                                            this.updateFilters({type: type, filter: validValues});

                                            // console.warn('B');
                                            // console.warn(type);
                                            // console.warn(validValues);
                                        }
                                    }
                                }
                            }
                            else {
                                //update the filter values for the current component selections
                                this.updateFilters({type: type, filter: values});
                            }
                    }
                }
                else {
                    const existingFilters = this.getFilterKeys.filter(k => k !== filterType);
                    for (const type of this.filterTypes) {
                        //if (type !== filterType && type != 'baseGraph') {
                        if (type !== filterType && type == 'queryClaimId') {
                        //if (type !== filterType) {
                             let filterIndex = this.displayData[componentName].findIndex(d => d.id === type);
                             //create a deep clone of the displayData for this type and collect unique values from the original data
                             let responseObject = {...this.displayData[componentName].find(d => d.id === type)};
                             responseObject.items = [...new Set(this.responseData.map(d => d[type]))];
                             //If the component items do not match the original items, update the component items with the original values
                             if (!existingFilters || !arraysMatch(this.displayData[componentName][filterIndex].items, responseObject.items)) {
                                 this.displayData[componentName][filterIndex].items = responseObject.items;
                             }
                        //Don't think this logic has the intended effect?
                         //} else if (this.getFilterByType(type) && this.getFilterByType(type).length > 0) {
                        } else if (filterType === type && this.getFilterByType(type).length > 0) {
                            // console.warn('filterType: ' + filterType);
                            // console.warn('removed: ' + type);
                            this.removeFilter({type: type});
                         }
                    }
                }
            },
            resetQueryForm() {
                //fetch the query values for all claim frames
                Object.assign(this.$data, initialState());
                //this.filterTypes = [DATATYPES.baseGraph.id, DATATYPES.topic.id, DATATYPES.subtopic.id];
                //this.filterTypes = [DATATYPES.baseGraph.id, DATATYPES.subtopic.id, DATATYPES.queryClaimId.id];
                this.filterTypes = [DATATYPES.subtopic.id, DATATYPES.queryClaimId.id];
                this.getFilterValues();

                //update the store variables in order to remove the filters
                for (const filter of this.filterTypes) {
                    this.removeFilter({type: filter});
                }
                this.updateFilterReset(false);
                this.updateFilterSubmission(true);
                this.updateRouteParams(PARAMETERNAMES.filter);
            },
            getFilterValues() {
                //As more components get added, this code will need to change in order to support API requests for the various components.
                const componentName = COMPONENTNAMES.multiSelect;
                API.getClaimFrameFilterValues().then(response => {
                    if (response.data.length > 0) {
                        this.displayData = {[componentName] : []};
                        this.responseData = response.data;

                        //for (const type of [DATATYPES.baseGraph, DATATYPES.topic, DATATYPES.subtopic ]) {
                        // for (const type of [DATATYPES.baseGraph, DATATYPES.subtopic, DATATYPES.queryClaimId ]) {
                        for (const type of [DATATYPES.subtopic, DATATYPES.queryClaimId ]) {
                            //remove duplicate values by creating a new set

                            this.displayData[componentName].push(
                                new ComponentData(
                                    type.id,
                                    //[...new Set(response.data.map(k => k[type.id]))],
                                    response.data.map(k => ({   text: k[type.text], 
                                                                value: k[type.id],
                                                                disabled: false})),
                                    type.display,
                                    type.display.toLowerCase(),
                                    false
                                )
                            )
                        }
                    }
                    else {
                        this.displayData = [];
                    }
                }).catch((error) => {
                    console.warn(error)
                });
            }
        }
    }
</script>

<style lang="scss">
    .multi-claim-filter-input .v-chip .v-chip__content {
        display: inline-block !important;
        overflow: hidden;
        white-space: nowrap;
        direction: ltr;
        text-overflow: ellipsis;
        line-height: 2.3em;
        vertical-align: middle;
    }
</style>
