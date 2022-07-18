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
  - Created by Nate Deml on 12/07/21
-->

<template>
    <div class="page-content">
        <div v-if="loading===true" class="loading-section">
            <v-progress-linear indeterminate></v-progress-linear>
            <div class="loading-message-clear" width="100%" style="align:center">{{statusMessage}}</div>
        </div>
        <div>
            <div class="tableContainer">
                <div class="kb-details">
                    <div class="kb-description-wrapper">
                        <div class="element-list-header">
                            <div class="primary-page-header-left">Claim Frame Details</div>
                        </div>
                    </div>
                </div>
                <table class="claimFrameDetailsTable">
                    <tr>
                        <td class="tableKeyCol">Topic</td>
                        <td class="tableKValCol">{{topic == null ? '' : topic}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Subtopic</td>
                        <td class="tableKValCol">{{subTopic == null ? '' : subTopic}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Claim Template</td>
                        <td class="tableKValCol">{{template == null ? '' : template}}</td>
                    </tr>                    
                    <tr>
                        <td class="tableKeyCol">Claim Statement</td>
                        <td class="tableKValCol">{{template == null ? '' : claimerStatement}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Document Extract</td>
                        <td class="tableKValCol">{{description == null ? '' : description}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Location</td>
                        <td class="tableKValCol">{{location == null ? '' : location}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Medium</td>
                        <td class="tableKValCol">{{claimMedium == null ? '' : claimMedium}}</td>
                    </tr>
                    <!-- <tr>
                        <td class="tableKeyCol">Importance</td>
                        <td class="tableKValCol">{{importance == null ? '' : importance}}%</td>
                    </tr> -->
                    <tr>
                        <td class="tableKeyCol">Date</td>
                        <td class="tableKValCol">{{dateTime == null ? '' : dateTime}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Truth Value</td>
                        <td class="tableKValCol">{{epestemic == null ? '' : epestemic}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Sentiment</td>
                        <td class="tableKValCol">{{sentiment == null ? '' : sentiment}}</td>
                    </tr>
                    <!-- <tr>
                        <td class="tableKeyCol">System</td>
                        <td class="tableKValCol">{{system == null ? '' : system}}</td>
                    </tr>
                    <tr>
                        <td class="tableKeyCol">Claim URI</td>
                        <td class="tableKValCol">{{claimURI == null ? '' : claimURI}}</td>
                    </tr> -->
                </table>
                <br/>
                <DataTable
                    :data="this.xVaribleData"
                    :tableConfiguration="buildXVarTableConfiguration()"
                    :statusMessage="statusMessage"
                    :paging="paging"
                    :hideFooter=true
                ></DataTable>
                <DataTable
                    :data="this.sourceDocData"
                    :tableConfiguration="buildSourceDocTableConfiguration()"
                    :statusMessage="statusMessage"
                    :paging="paging"
                    :hideFooter=true
                ></DataTable>
                <DataTable
                    :data="this.claimerData"
                    :tableConfiguration="buildClaimerTableConfiguration()"
                    :statusMessage="statusMessage"
                    :paging="paging"
                    :hideFooter=true
                ></DataTable>
                <!-- <DataTable
                    :data="this.provenanceData"
                    :tableConfiguration="buildProvenanceTableConfiguration()"
                    :statusMessage="statusMessage"
                    :paging="paging"
                    :hideFooter=true
                ></DataTable> -->


                <!-- <DataTable
                    :data="this.associatdeKeData"
                    :tableConfiguration="buildAssociatedKEsTableConfiguration()"
                    :statusMessage="statusMessage"
                    :paging="paging"
                    :hideFooter=true
                ></DataTable> -->

                <br>

                <!-- <div v-if="this.dupClaimsData.length > 0 || this.suppClaimsData.length > 0 || this.relClaimsData.length > 0 || this.refuteClaimsData.length > 0">
                    <h3>Identical, Supporting, Refuting, and Related Claims</h3>
                </div>

                <div v-if="this.dupClaimsData.length > 0">
                    <DataTable
                        :data="this.dupClaimsData"
                        :tableConfiguration="buildRelatedClaimTableConfiguration('Identical Claims')"
                        :statusMessage="statusMessage"
                        :paging="paging"
                        :hideFooter=true
                    ></DataTable>
                </div>
                <div v-else>
                    <br/>
                    <h5>There are no Identical Claims</h5>
                </div>

                <div v-if="this.suppClaimsData.length > 0">
                    <DataTable
                        :data="this.suppClaimsData"
                        :tableConfiguration="buildRelatedClaimTableConfiguration('Supporting Claims')"
                        :statusMessage="statusMessage"
                        :paging="paging"
                        :hideFooter=true
                    ></DataTable>
                </div>
                <div v-else>
                    <br/>
                    <h5>There are no Supporting Claims</h5>
                </div>

                <div v-if="this.refuteClaimsData.length > 0">
                    <DataTable
                        :data="this.refuteClaimsData"
                        :tableConfiguration="buildRelatedClaimTableConfiguration('Refuting Claims')"
                        :statusMessage="statusMessage"
                        :paging="paging"
                        :hideFooter=true
                    ></DataTable>
                </div>
                <div v-else>
                    <br/>
                    <h5>There are no Refuting Claims</h5>
                </div>

                <div v-if="this.relClaimsData.length > 0">
                    <DataTable
                        :data="this.relClaimsData"
                        :tableConfiguration="buildRelatedClaimTableConfiguration('Related Claims')"
                        :statusMessage="statusMessage"
                        :paging="paging"
                        :hideFooter=true
                    ></DataTable>
                </div>
                <div v-else>
                    <br/>
                    <h5>There are no Related Claims</h5>
                </div> -->

            </div>
        </div>
    </div>
</template>

<script>
    import API from '../../api';
    import DataTable from '@/components/DataTable';
    import {
        TABLEFIELDMAPPING,
        TABLESELECTIONS,
        NODATAMESSAGE,
        TableSettings,
        HeaderConfig,
        getDateString,
        makePercentage,
        SecondaryExpand,
        getId,
        splitOnUpper
    } from '../../utils';

    function initialState() {
        return {
            claim: null,
            loading: true,
            statusMessage: 'Loading Data...',
            topic: '',
            subTopic: '',
            template: '',
            claimerStatement: '',
            claimer: '',
            xVar: '',
            description: '',
            xVaribleData: [],
            sourceDocData: [],
            provenanceData: [],
            dupClaimsData: [],
            suppClaimsData: [],
            relClaimsData: [],
            refuteClaimsData: [],
            claimerData: [],
            claimerAffilationData: [],
            associatdeKeData: [],
            primaryHeaderData: [],
            location: '',
            claimMedium: '',
            importance: '',
            dateTime: '',
            epestemic: '',
            sentiment: '',
            system: '',
            claimURI: '',
            paging: {[TABLESELECTIONS.itemsPerPage]:'All'}
        }
    }

    export default {
  components: { DataTable },
        name: 'claimFrameDetails',
        data: () => (initialState()),
        created() {
            this.NODATAMESSAGE = NODATAMESSAGE;
            this.setClaimFrameDetails(this.$route.params.claimid);
        },
        methods: {
            setClaimFrameDetails(claimFrameId) {
                API.getClaimFrameById(claimFrameId).then(response => {
                    const claimFrameDetails = response.data;

                    if(Object.keys(claimFrameDetails).length > 0) {
                        this.topic = claimFrameDetails.topic;
                        this.subTopic = claimFrameDetails.subtopic;
                        this.template = claimFrameDetails.claimTemplate;
                        this.description = claimFrameDetails.description;
                        this.importance = makePercentage(claimFrameDetails.importance, 0);
                        this.epestemic = splitOnUpper(getId(claimFrameDetails.epistemic)).replace('Epistemic', '');
                        this.sentiment = splitOnUpper(getId(claimFrameDetails.sentiment)).replace('Sentiment', '');
                        this.system = getId(claimFrameDetails.system);
                        this.dateTime = getDateString(claimFrameDetails.dates[0]);
                        this.claimURI = claimFrameDetails.claimURI;

                        let tempSourceDocVar = {};
                        tempSourceDocVar[TABLEFIELDMAPPING.docId] = claimFrameDetails.sourceDocument.id;
                        tempSourceDocVar[TABLEFIELDMAPPING.docTitle] = claimFrameDetails.sourceDocument.title;
                        tempSourceDocVar[TABLEFIELDMAPPING.docContentDate] = claimFrameDetails.sourceDocument.contentDate;
                        tempSourceDocVar[TABLEFIELDMAPPING.docDownloadDate] = claimFrameDetails.sourceDocument.downloadDate;
                        this.sourceDocData.push(tempSourceDocVar)

                        for (const component of claimFrameDetails.components) {
                            if(component.propertyName === 'xVariable') {
                                for (const propComponent of component.values) {
                                    let tempXVar = {};

                                    tempXVar[TABLEFIELDMAPPING.names] = propComponent.componentName;
                                    tempXVar[TABLEFIELDMAPPING.id] = propComponent.componentId;
                                    tempXVar[TABLEFIELDMAPPING.types] = propComponent.componentTypes;
                                    tempXVar[TABLEFIELDMAPPING.provenance] = propComponent.componentProvenance;
                                    tempXVar[TABLEFIELDMAPPING.ke] = getId(propComponent.componentKE);

                                    this.xVariable = propComponent.componentName

                                    this.xVar = propComponent.componentName;
                                    this.xVaribleData.push(tempXVar)
                                }
                            } else if(component.propertyName === 'claimer') {
                                for (const propComponent of component.values) {
                                    let tempClaimer = {};

                                    tempClaimer[TABLEFIELDMAPPING.category] = 'Claimer';
                                    tempClaimer[TABLEFIELDMAPPING.names] = propComponent.componentName;
                                    tempClaimer[TABLEFIELDMAPPING.id] = propComponent.componentId;
                                    tempClaimer[TABLEFIELDMAPPING.types] = propComponent.componentTypes;
                                    tempClaimer[TABLEFIELDMAPPING.provenance] = propComponent.componentProvenance;
                                    tempClaimer[TABLEFIELDMAPPING.ke] = getId(propComponent.componentKE);

                                    this.claimer = propComponent.componentName

                                    if(this.primaryHeaderData[TABLEFIELDMAPPING.claimerList] === undefined) {
                                        this.primaryHeaderData[TABLEFIELDMAPPING.claimerList] = [propComponent.componentName]
                                    } else {
                                        this.primaryHeaderData[TABLEFIELDMAPPING.claimerList].push(propComponent.componentName)
                                    }

                                    this.claimerData.push(tempClaimer)
                                }
                            // } else if(component.propertyName === 'associatedKEs') {
                            //     for (const propComponent of component.values) {
                            //         let tempClaimer = {};

                            //         tempClaimer[TABLEFIELDMAPPING.id] = propComponent.keId;
                            //         tempClaimer[TABLEFIELDMAPPING.category] = propComponent.category;
                            //         tempClaimer[TABLEFIELDMAPPING.types] = propComponent.types;


                            //         this.associatdeKeData.push(tempClaimer)
                            //     }
                            } else if(component.propertyName === 'claimerAffiliation' && component.values !== null) {
                                for (const propComponent of component.values) {
                                    let tempClaimerAff = {};

                                    tempClaimerAff[TABLEFIELDMAPPING.category] = 'Claimer Affiliation';
                                    tempClaimerAff[TABLEFIELDMAPPING.names] = propComponent.componentName;
                                    tempClaimerAff[TABLEFIELDMAPPING.id] = propComponent.componentId;
                                    tempClaimerAff[TABLEFIELDMAPPING.types] = propComponent.componentTypes;
                                    tempClaimerAff[TABLEFIELDMAPPING.provenance] = propComponent.componentProvenance;
                                    tempClaimerAff[TABLEFIELDMAPPING.ke] = getId(propComponent.componentKE);

                                    if(this.primaryHeaderData[TABLEFIELDMAPPING.affiliation] === undefined) {
                                        this.primaryHeaderData[TABLEFIELDMAPPING.affiliation] = [propComponent.componentName]
                                    } else {
                                        this.primaryHeaderData[TABLEFIELDMAPPING.affiliation].push(propComponent.componentName)
                                    }

                                    this.claimerAffilationData.push(tempClaimerAff)
                                }
                            } else if(component.propertyName === 'claimLocation') {
                                //this.location = component.values[0].componentName
                                this.location = component.values === null ? '' : component.values[0].componentName;
                            } else if(component.propertyName === 'claimMedium') {
                                //this.location = component.values[0].componentName
                                this.claimMedium = component.values === null ? '' : component.values[0].componentName;
                            } else if(component.propertyName == null) {
                                continue;
                            }
                        }

                        // for (const tempProvenance of claimFrameDetails.provenances) {
                        //     if(tempProvenance.propertyName === 'associatedKEs') {
                        //         for (const provArray of tempProvenance.values) {
                        //             // if(this.primaryHeaderData[TABLEFIELDMAPPING.variableList] === undefined) {
                        //             //     this.primaryHeaderData[TABLEFIELDMAPPING.variableList] = [];
                        //             //     this.primaryHeaderData[TABLEFIELDMAPPING.variableList].push(getId(provArray.keId))
                        //             // } else {
                        //             //     this.primaryHeaderData[TABLEFIELDMAPPING.variableList].push(getId(provArray.keId))
                        //             // }
                        //             let tempClaimer = {};

                        //             tempClaimer[TABLEFIELDMAPPING.id] = provArray.keId;
                        //             tempClaimer[TABLEFIELDMAPPING.category] = provArray.category;
                        //             tempClaimer[TABLEFIELDMAPPING.types] = provArray.types;

                        //             this.associatdeKeData.push(tempClaimer)
                        //         }
                        //     } else if(tempProvenance.propertyName === 'claimSemantics') {
                        //         for (const provArray of tempProvenance.values) {
                        //             if(this.primaryHeaderData[TABLEFIELDMAPPING.semantics] === undefined) {
                        //                 this.primaryHeaderData[TABLEFIELDMAPPING.semantics] = [];
                        //                 this.primaryHeaderData[TABLEFIELDMAPPING.semantics].push(getId(provArray.keId))
                        //             } else {
                        //                 this.primaryHeaderData[TABLEFIELDMAPPING.semantics].push(getId(provArray.keId))
                        //             }
                        //         }
                        //     }
                        // }

                        let tTemplate = ((' ' + this.template + ' ').replace(' X ', ' ' + this.xVariable + ' ')).trim();
                        this.claimerStatement = this.claimer + ' claim ' + tTemplate;

                        let secondaryData = this.claimerData;
                        secondaryData = secondaryData.concat(this.claimerAffilationData);
                        this.primaryHeaderData[TABLEFIELDMAPPING.entityList] = claimFrameDetails.sourceDocument.id;
                        this.primaryHeaderData[TABLEFIELDMAPPING.documentList] = secondaryData;
                        this.provenanceData = [this.primaryHeaderData];

                        this.claimerData[0][TABLEFIELDMAPPING.documentList] = this.claimerAffilationData

                        //REMOVED FOR CONDITION 5
                        // for (const relatedClaims of claimFrameDetails.relations) {
                        //     if(relatedClaims.propertyName === 'identicalClaims') {
                        //         if (relatedClaims.values != null) {
                        //             for (const relatedValues of relatedClaims.values) {
                        //                 let tempRelatedClaim = {};

                        //                 tempRelatedClaim[TABLEFIELDMAPPING.headline] = relatedValues.description === null ? '' : relatedValues.description;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.variableList] = [relatedValues.xVariable === null ? '' : relatedValues.xVariable];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.location] = relatedValues.claimLocation === null ? '' : relatedValues.claimLocation;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.percentage] = makePercentage(relatedValues.importance === null ? '' : relatedValues.importance, 0);
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.dates] = [relatedValues.dates == null ? '' : getDateString(relatedValues.dates[0])];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.id] = relatedValues.claimId === null ? '' : relatedValues.claimId;

                        //                 if(tempRelatedClaim[TABLEFIELDMAPPING.headline] === '' ) {
                        //                     continue;
                        //                 } else {
                        //                     this.dupClaimsData.push(tempRelatedClaim)
                        //                 }
                        //             }
                        //         }
                        //     } else if(relatedClaims.propertyName === 'supportingClaims') {
                        //         if (relatedClaims.values != null) {
                        //             for (const relatedValues of relatedClaims.values) {
                        //                 let tempRelatedClaim = {};

                        //                 tempRelatedClaim[TABLEFIELDMAPPING.headline] = relatedValues.description === null ? '' : relatedValues.description;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.variableList] = [relatedValues.xVariable === null ? '' : relatedValues.xVariable];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.location] = relatedValues.claimLocation === null ? '' : relatedValues.claimLocation;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.percentage] = makePercentage(relatedValues.importance === null ? '' : relatedValues.importance, 0);
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.dates] = [relatedValues.dates == null ? '' : getDateString(relatedValues.dates[0])];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.id] = relatedValues.claimId === null ? '' : relatedValues.claimId;

                        //                 if(tempRelatedClaim[TABLEFIELDMAPPING.headline] === '' ) {
                        //                     continue;
                        //                 } else {
                        //                     this.suppClaimsData.push(tempRelatedClaim)
                        //                 }
                        //             }
                        //         }
                        //     } else if(relatedClaims.propertyName === 'relatedClaims') {
                        //         if (relatedClaims.values != null) {
                        //             for (const relatedValues of relatedClaims.values) {
                        //                 let tempRelatedClaim = {};

                        //                 tempRelatedClaim[TABLEFIELDMAPPING.headline] = relatedValues.description === null ? '' : relatedValues.description;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.variableList] = [relatedValues.xVariable === null ? '' : relatedValues.xVariable];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.location] = relatedValues.claimLocation === null ? '' : relatedValues.claimLocation;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.percentage] = makePercentage(relatedValues.importance === null ? '' : relatedValues.importance, 0);
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.dates] = [relatedValues.dates == null ? '' : getDateString(relatedValues.dates[0])];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.id] = relatedValues.claimId === null ? '' : relatedValues.claimId;

                        //                 if(tempRelatedClaim[TABLEFIELDMAPPING.headline] === '' ) {
                        //                     continue;
                        //                 } else {
                        //                     this.relClaimsData.push(tempRelatedClaim)
                        //                 }
                        //             }
                        //         }
                        //     } else if(relatedClaims.propertyName === 'refutingClaims') {
                        //         if (relatedClaims.values != null) {
                        //             for (const relatedValues of relatedClaims.values) {
                        //                 let tempRelatedClaim = {};

                        //                 tempRelatedClaim[TABLEFIELDMAPPING.headline] = relatedValues.description === null ? '' : relatedValues.description;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.variableList] = [relatedValues.xVariable === null ? '' : relatedValues.xVariable];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.location] = relatedValues.claimLocation === null ? '' : relatedValues.claimLocation;
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.percentage] = makePercentage(relatedValues.importance === null ? '' : relatedValues.importance, 0);
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.dates] = [relatedValues.dates == null ? '' : getDateString(relatedValues.dates[0])];
                        //                 tempRelatedClaim[TABLEFIELDMAPPING.id] = relatedValues.claimId === null ? '' : relatedValues.claimId;

                        //                 if(tempRelatedClaim[TABLEFIELDMAPPING.headline] === '' ) {
                        //                     continue;
                        //                 } else {
                        //                     this.refuteClaimsData.push(tempRelatedClaim)
                        //                 }
                        //             }
                        //         }
                        //     }
                        // }

                    } else {
                        this.statusMessage = this.NODATAMESSAGE;
                        this.loading = false;
                    }
                    this.loading = false;
                })
            },
            //TODO: ComponentTypes can just be one function
            buildXVarTableConfiguration() {
                return {
                    tableSettings: new TableSettings(TABLEFIELDMAPPING.id, false, 'Claim Value', '', false, false, false, TABLEFIELDMAPPING.names, true),
                    primaryHeaders: [
                        new HeaderConfig('Name', 'start', true, TABLEFIELDMAPPING.names),
                        // new HeaderConfig('Identity', 'start', true, TABLEFIELDMAPPING.id, true, false, 'https://kgtk.isi.edu/api?q=', TABLEFIELDMAPPING.xvariable),
                        // new HeaderConfig('Type', 'start', true, TABLEFIELDMAPPING.types),
                        new HeaderConfig('Provenance', 'start', true, TABLEFIELDMAPPING.provenance),
                        //new HeaderConfig('KE', 'start', true, TABLEFIELDMAPPING.ke)
                    ]
                };
            },
            buildSourceDocTableConfiguration() {
                return {
                    tableSettings: new TableSettings(TABLEFIELDMAPPING.docId, false, 'Source Document', '', false, false, false, TABLEFIELDMAPPING.docTitle, true),
                    primaryHeaders: [
                        new HeaderConfig('Title', 'start', true, TABLEFIELDMAPPING.docTitle),
                        // new HeaderConfig('ID', 'start', true, TABLEFIELDMAPPING.docId),
                        new HeaderConfig('Content Date', 'start', true, TABLEFIELDMAPPING.docContentDate),
                        new HeaderConfig('Download Date', 'start', true, TABLEFIELDMAPPING.docDownloadDate)
                    ]
                };
            },
            buildClaimerTableConfiguration() {
                return {
                    tableSettings: new TableSettings(TABLEFIELDMAPPING.names, true, 'Claimer', '', false, false, false, TABLEFIELDMAPPING.names, true),
                    primaryHeaders: [
                                new HeaderConfig('Name', 'start', true, TABLEFIELDMAPPING.names),
                                // new HeaderConfig('Identity', 'start', true, TABLEFIELDMAPPING.id),
                                // new HeaderConfig('Type', 'start', true, TABLEFIELDMAPPING.types),
                                new HeaderConfig('Provenance', 'start', true, TABLEFIELDMAPPING.provenance),
                                //new HeaderConfig('KE', 'start', true, TABLEFIELDMAPPING.ke),
                                new HeaderConfig('', 'center', false, 'data-table-expand')
                    ],
                    secondaryHeaders: {
                        expanded: new SecondaryExpand('', TABLEFIELDMAPPING.documentList,
                            [
                                new HeaderConfig('Claimer Association', 'start', true, TABLEFIELDMAPPING.names),
                                // new HeaderConfig('Identity', 'start', true, TABLEFIELDMAPPING.id),
                                // new HeaderConfig('Type', 'start', true, TABLEFIELDMAPPING.types),
                                new HeaderConfig('Provenance', 'start', true, TABLEFIELDMAPPING.provenance),
                                //new HeaderConfig('KE', 'start', true, TABLEFIELDMAPPING.ke)
                            ]
                        )
                    }
                };
            },
            // buildAssociatedKEsTableConfiguration() {
            //     return {
            //         tableSettings: new TableSettings(TABLEFIELDMAPPING.id, false, 'Associated KEs', '', false, false, false, TABLEFIELDMAPPING.category, true),
            //         primaryHeaders: [
            //             new HeaderConfig('URI', 'start', true, TABLEFIELDMAPPING.id),
            //             new HeaderConfig('ERE Type', 'start', true, TABLEFIELDMAPPING.category),
            //             new HeaderConfig('Types', 'start', true, TABLEFIELDMAPPING.types)
            //         ]
            //     };
            // },

            buildProvenanceTableConfiguration() {
                // Use primary and secondary headers format the data for the secondary headers.
                return {
                    tableSettings: new TableSettings(TABLEFIELDMAPPING.id, true, 'Provenance', '', false, false, false, TABLEFIELDMAPPING.percentage, true),
                    primaryHeaders: [
                        new HeaderConfig('Claimer', 'start', true, TABLEFIELDMAPPING.claimerList),
                        new HeaderConfig('Claimer Affiliation', 'start', true, TABLEFIELDMAPPING.affiliation),
                        new HeaderConfig('Source', 'start', true, TABLEFIELDMAPPING.entityList),
                        new HeaderConfig('Associated KE', 'start', true, TABLEFIELDMAPPING.variableList),
                        new HeaderConfig('Claim Semantics', 'start', true, TABLEFIELDMAPPING.semantics),
                        new HeaderConfig('', 'center', false, 'data-table-expand')
                    ],
                    secondaryHeaders: {
                        expanded: new SecondaryExpand('', TABLEFIELDMAPPING.documentList,
                            [
                                new HeaderConfig('Component', 'start', false, TABLEFIELDMAPPING.category),
                                new HeaderConfig('Name', 'start', false, TABLEFIELDMAPPING.names),
                                // new HeaderConfig('Identity', 'start', false, TABLEFIELDMAPPING.id),
                                // new HeaderConfig('Type', 'start', false, TABLEFIELDMAPPING.types),
                                new HeaderConfig('Provenance', 'start', false, TABLEFIELDMAPPING.provenance),
                                new HeaderConfig('KE', 'start', false, TABLEFIELDMAPPING.ke)
                            ]
                        )
                    }
                };
            },
            buildRelatedClaimTableConfiguration(relationship) {
                return {
                    tableSettings: new TableSettings(TABLEFIELDMAPPING.id, false, relationship, '', false, false, false, TABLEFIELDMAPPING.percentage, true),
                    primaryHeaders: [
                        new HeaderConfig('Description', 'start', true, TABLEFIELDMAPPING.headline),
                        new HeaderConfig('Claim Value', 'start', true, TABLEFIELDMAPPING.variableList),
                        new HeaderConfig('Location', 'start', true, TABLEFIELDMAPPING.location),
                        // new HeaderConfig('Importance', 'start', true, TABLEFIELDMAPPING.percentage),
                        new HeaderConfig('Date', 'start', true, TABLEFIELDMAPPING.dates)
                    ]
                };
            }
        },
    }
</script>
