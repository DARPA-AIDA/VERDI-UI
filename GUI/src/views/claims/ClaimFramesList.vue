<!--
  - Copyright 2021 Next Century Corporation/CACI
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
  - Created by s.nordt on 11/23/21
-->

<template>
    <div class="page-content">
        <div class="kb-details">
            <div class="kb-description-wrapper">
                <div class="element-list-header">
                    <div class="primary-page-header-left">Claims</div>
                </div>
            </div>
        </div>
        <div v-if="loading===true" class="loading-section">
            <v-progress-linear indeterminate></v-progress-linear>
            <div class="loading-message-clear">{{statusMessage}}</div>
        </div>
        <div v-else>
            <div v-if="statusMessage === NODATAMESSAGE"  class="loading-message-clear">{{statusMessage}}</div>
            <!-- Note this code here '< 3', '< 2' when we remove TA3 Input selection -->
            <div v-else-if="this.getFilterKeys.length < 2 && this.filterSubmission"></div>
            <div v-else-if="topics && Object.keys(topics).length > 0 && claimFrames && Object.keys(claimFrames).length > 0">
                <div v-for="(topicIndex) in Object.keys(topics)" :key="topicIndex" class="topic-space">
                    <div class="element-list-header">

                        <div class="page-header-left topic-title">{{claimFrames[topicIndex]["variables"][0].topic + ' : ' + claimFrames[topicIndex]["variables"][0].subtopic}}</div>
                        <div class="page-header-right">
                            <div class="button-icon" @click="toggleTopicSection(topicIndex)">
                                <InfoTooltip :ref="getRefId(topicIndex)" :message="toggleTopics[getRefId(topicIndex)].message" :icon="toggleTopics[getRefId(topicIndex)].icon" :iconStyle="'teal-large'"></InfoTooltip>
                            </div>
                        </div>
                    </div>
                    <div v-for="(subTopic, subTopicIndex) in topics[topicIndex]" :key="subTopicIndex" :class="subTopicIndex > 0 && !toggleTopics[getRefId(topicIndex)].closed ? 'section-space' : ''">
                        <DataTable
                            :data="tableData[topicIndex][subTopic]"
                            :paging="paging"
                            :expand="false"
                            :tableConfiguration="buildTableConfiguration(tableData[topicIndex])"
                            :statusMessage="statusMessage"
                            :hideData="toggleTopics[getRefId(topicIndex)].closed"
                            :hideFooter=true
                        ></DataTable>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import API from '../../api';
    import DataTable from '@/components/DataTable';
    import InfoTooltip from '@/components/InfoTooltip';
    import { mapGetters, mapMutations, mapState } from 'vuex';
    import {
        TABLEFIELDMAPPING,
        TABLESELECTIONS,
        NODATAMESSAGE,
        DATATYPES,
        //sortCaseInsensitive,
        pluralize,
        TableSettings,
        getDateString,
        HeaderConfig,
        SecondaryExpand,
        //makePercentage
    } from '../../utils';

    function initialState() {
        return {
            paging: {[TABLESELECTIONS.itemsPerPage]:'All'},
            statusMessage: '',
            claims: {},
            claimTopics: {},
            tableData: [],
            filterTypes: [],
            loading: false,
            toggleTables: {},
            toggleSettings: {minimize: {message:'Minimize Section', icon:'mdi-chevron-up'}, maximize: {message:'Maximize Section', icon:'mdi-chevron-down'}},
            outerClaims: {},
            middleClaims: {},
            innerClaims: {}
        }
    }
    export default {
        name: 'ClaimFramesList',
        components: {DataTable, InfoTooltip},
        data: () => (initialState()),
        created() {
            this.NODATAMESSAGE = NODATAMESSAGE;
            this.TABLEFIELDMAPPING = TABLEFIELDMAPPING;
            //Remove PER Lisa/MITRE this.filterTypes = [DATATYPES.baseGraph.id, DATATYPES.topic.id, DATATYPES.subtopic.id];
            //this.filterTypes = [DATATYPES.baseGraph.id, DATATYPES.subtopic.id, DATATYPES.queryClaimId.id];
            this.filterTypes = [DATATYPES.subtopic.id, DATATYPES.queryClaimId.id];
        },
        mounted() {
            this.setClaims();
            //this.initClaims();

        },
        computed: {
            ...mapGetters(['getFilterKeys']),
            ...mapState(['filters', 'filterSubmission']),
            topics() {
                return this.claimTopics;
            },
            claimFrames() {
                if(this.filterSubmission) {
                    this.setClaims();
                }

                return Object.keys(this.claims).length > 0 ? this.claims : null;
            },
            toggleTopics() {
                return Object.keys(this.toggleTables).length > 0 ? this.toggleTables : null;
            }
        },
        methods: {
            ...mapMutations(['updateFilterSubmission']),
            completedSearch(value) {
                console.warn('completed search', value)
            },
            getRefId(id) {
                return 'claimTopicTooltip' + id;
            },
            buildTableConfiguration(topic) {
                //let displaySubtopic = topic.variables[0].subtopic
                let displayTemplate = topic.variables[0].claimTemplate;

                return {
                    tableSettings: new TableSettings(TABLEFIELDMAPPING.variableList, true, displayTemplate, '' , false, false, false, TABLEFIELDMAPPING.count, true),
                    primaryHeaders: [
                        new HeaderConfig('X Value', 'start', true, TABLEFIELDMAPPING.variableList),
                        new HeaderConfig('Number of Claimers', 'start', true, TABLEFIELDMAPPING.count),
                        // new HeaderConfig('Claimer Affiliation', 'start', true, TABLEFIELDMAPPING.affiliation),
                        new HeaderConfig('', 'center', false, 'data-table-expand')
                    ],
                    secondaryHeaders: {
                        expanded: new SecondaryExpand('', TABLEFIELDMAPPING.documentList,
                            [
                                new HeaderConfig('Claims', 'start', false, TABLEFIELDMAPPING.claimer),
                                new HeaderConfig('Truth Value', 'start', false, TABLEFIELDMAPPING.epistemic),
                                new HeaderConfig('Relation', 'start', false, TABLEFIELDMAPPING.claimRelations),
                                //new HeaderConfig('Document', 'start', false, TABLEFIELDMAPPING.sourceDoc),
                                //new HeaderConfig('Claimer Affiliation', 'start', false, TABLEFIELDMAPPING.affiliation),
                                new HeaderConfig('Document Title and Extract', 'start', false, TABLEFIELDMAPPING.sorceDocTitleExt),
                                //new HeaderConfig('Location', 'start', false, TABLEFIELDMAPPING.location),
                                //Removed Per DARPA: new HeaderConfig('Importance', 'start', false, TABLEFIELDMAPPING.percentage),
                                new HeaderConfig('Date', 'start', false, TABLEFIELDMAPPING.dates),
                                new HeaderConfig('Details', 'start', false, TABLEFIELDMAPPING.id)
                            ]
                        )
                    }
                }
            },
            toggleTopicSection(index) {
                const toggleId = this.getRefId(index);
                if(this.$refs[toggleId].length > 0) {
                    const el = this.$refs[toggleId][0];
                    if(this.toggleTables.hasOwnProperty(toggleId)) {
                        if(el.icon === 'mdi-chevron-up') {
                            this.toggleTables[toggleId].message = this.toggleSettings.maximize.message;
                            this.toggleTables[toggleId].icon = this.toggleSettings.maximize.icon;
                        }
                        else {
                            this.toggleTables[toggleId].message = this.toggleSettings.minimize.message;
                            this.toggleTables[toggleId].icon = this.toggleSettings.minimize.icon;
                        }
                        this.toggleTables[toggleId].closed = !this.toggleTables[toggleId].closed;
                    }
                }
            },
            setClaims() {
                //updates the claims data and member types
                this.claimTopics = {};
                this.claims = {};
                let toggleData = {};
                this.loading = true;
                this.updateFilterSubmission(false);
                const claimFilters = {};
                this.tableData = [];

                //if filters exist, create formatted claims to submit as API parameters
                const filterKeys = this.getFilterKeys;

                if (filterKeys.length > 0) {
                    for (const type of this.filterTypes) {
                        if(filterKeys.includes(type)) {
                            claimFilters[pluralize(type)] = this.filters[type];
                        }
                    }
                }

                if (filterKeys.length == 0) {
                    // claimFilters['baseGraphs'] = ['https://www.nextcentury.com/TA3/BBN/BBN/BBN/'];
                    claimFilters['baseGraphs'] = ['XXX'];
                    claimFilters['subtopics'] = [];
                    claimFilters['queryClaimIds'] = ['CLL0C04G510.000030'];
                }


                console.warn('claimFilter');
                console.warn(claimFilters);


                API.getClaimFrames(claimFilters).then(response => {
                    const claimFrameData = response.data;

                    let topicLayer = new Set();
                    let outer = new Set();
                    let middle = new Set();

                    for (const claimFrame of claimFrameData) {

                        let topicSubTopicStr = claimFrame.topic + ' : ' + claimFrame.subtopic;

                        //unque topic/subtopic layer (convert to str for unique)
                        topicLayer.add(JSON.stringify({topic: claimFrame.topic,
                                                    subtopic: claimFrame.subtopic,
                                                    claimTemplate: claimFrame.claimTemplate,
                                                    topicSubTopicStr: topicSubTopicStr}))

                        //Get top layer - unique list of Topic, SubTopic, claimTemplate, xvariables (convert to str for unique)
                        // outer.add(JSON.stringify({topic:claimFrame.topic, subtopic: claimFrame.subtopic, claimTemplate: claimFrame.claimTemplate, variables: [claimFrame.xVariable], variableId: claimFrame.xVariableCompId}))
                        outer.add(JSON.stringify({topic:claimFrame.topic, subtopic: claimFrame.subtopic, claimTemplate: claimFrame.claimTemplate, variableId: claimFrame.xVariableCompId}))

                        //create middle layer - X Variable, Number of Claimers, Claimer Affiliation
                        let claimerStatement = (claimFrame.claimer + ' claims ' + claimFrame.claimTemplate + ' ').replace(' X ', ' ' + claimFrame.xVariable + ' ').trim()
                        middle.add({topic:claimFrame.topic, subtopic: claimFrame.subtopic, claimTemplate: claimFrame.claimTemplate, variables: claimFrame.xVariable, variableId: claimFrame.xVariableCompId,
                                    claimerAffiliation: claimFrame.claimerAffiliation, claimer: claimerStatement, description: claimFrame.description,
                                    locationName: claimFrame.locationName, importance: claimFrame.importance, claimId: claimFrame.claimId, id: claimFrame.claimId,
                                    dates: claimFrame.dates, sourceDoc: claimFrame.sourceDoc, epistemic: claimFrame.epistemic.replace('Uncertain', '').replace('Certain',''), queryClaimId: claimFrame.queryClaimId, claimRelations: claimFrame.claimRelations, sourceDocTitle: claimFrame.sourceDocTitle});
                    }

                    //Get topic layer - unique list of Topic, SubTopic, claimTemplate - (convert back to set)
                    let topicSet = [...topicLayer].map( item => {
                        if (typeof item === 'string') return JSON.parse(item);
                        else if (typeof item === 'object') return item;
                    });

                    //sort topic list by topic
                    topicSet = topicSet.sort(function (obj1, obj2) {
                        return obj1.topicSubTopicStr.localeCompare(obj2.topicSubTopicStr);
                    });

                 

                    //Get outer layer - unique list of Topic, SubTopic, claimTemplate - (convert back to set)
                    let outerSet = [...outer].map( item => {
                        if (typeof item === 'string') return JSON.parse(item);
                        else if (typeof item === 'object') return item;
                    });

                    //sort middle list by topic
                    let middleSet = Array.from(middle);
                    //sort topic list by topic
                    middleSet = middleSet.sort(function (obj1, obj2) {
                        return obj1.claimer.localeCompare(obj2.claimer);
                    });                       

                    let tableDataSet = new Set();
                    let topicCount = 0;
                    for (const topicItem of topicSet) {

                        let variablesSet = new Set();
                        for (const outerItem of outerSet) {
                            if (outerItem.topic == topicItem.topic && outerItem.subtopic == topicItem.subtopic) {
                                let docSet = new Set();
                                let docCount = 0;
                                let affSet = new Set();

                                //Change X-Variable to match other QNodes
                                let setX = '';
                                for (const middleItem of middleSet) {

                                    //if (outerItem.topic == middleItem.topic && outerItem.subtopic == middleItem.subtopic && outerItem.variables == middleItem.variables) {
                                    if (outerItem.topic == middleItem.topic && outerItem.subtopic == middleItem.subtopic && outerItem.variableId === middleItem.variableId) {

                                        //Change X-Variable to match other QNodes
                                        if ( setX == '' ) {
                                            setX = middleItem.variables;
                                        }

                                        docCount++;
                                        let startDate = middleItem.dates ? getDateString(middleItem.dates[0]) : 'EMPTY';
                                        docSet.add({claimer: middleItem.claimer,  affiliation: [middleItem.claimerAffiliation], description: middleItem.description, locationName: middleItem.locationName,
                                        //percentage: middleItem.importance, date: startDate, claimId: middleItem.claimId, id: middleItem.claimId, variables: middleItem.variables});
                                        percentage: middleItem.importance, dates: startDate, claimId: middleItem.claimId, id: middleItem.claimId, variables: setX, 
                                            epistemic: middleItem.epistemic, sourceDoc: middleItem.sourceDoc, queryClaimId: middleItem.queryClaimId, claimRelations: middleItem.claimRelations, sourceDocTitle: middleItem.sourceDocTitle});
                                        affSet.add(middleItem.claimerAffiliation);
                                    }

                                }

                                outerItem['count'] = docCount;
                                outerItem['affiliation'] = Array.from(affSet);
                                outerItem['docs'] = Array.from(docSet);

                                //Change X-Variable to match other QNodes
                                outerItem['variables'] = [setX];

                                variablesSet.add(outerItem);
                            }
                        }

                        let x  = [];
                        x['variables'] = Array.from(variablesSet);
                        tableDataSet.add(x);

                        //TODO: Relook at these
                        this.claimTopics[topicCount] = ['variables'];
                        toggleData[this.getRefId(topicCount)] = {message : this.toggleSettings.maximize.message, icon : this.toggleSettings.maximize.icon, closed : (Object.keys(claimFilters).length === 0)};
                        //Default to closed
                        toggleData[this.getRefId(topicCount)].closed = true;

                        topicCount++;
                    }

                    this.toggleTables = toggleData;

                    //TODO: Claims and Table Data are the same things? Remove one
                    this.tableData = Array.from(tableDataSet);
                    this.claims = Array.from(tableDataSet);

                    this.loading = false;
                }).catch((error) => {
                    console.warn(error)
                });
            },
        }
    }
</script>

<style lang="scss" scoped>
    .query-div {
        @extend .kb-description-wrapper;
        padding: 10px 0;
        justify-content: space-between;
    }

    .query-field-column-wrapper{
        @include flex-column-nowrap;
        padding : 0 4px 6px 0;
    }

    .loading-section {
        margin: 48px 0;
    }

    .loading-message-clear {
        @include flex-row-nowrap;
        justify-content: center;
        height: 60px;
        padding: 16px;
    }

    .topic-space:not(:first-child) {
        margin: 40px 0 32px;
    }

    .topic-title {
        font-size: 18px;
        @extend .bold-text;
        width: 100%;
        color: $theme-teal-text;
        line-height: 32px;
        font-style: italic;
    }

</style>

