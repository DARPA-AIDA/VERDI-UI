<template>
  <div class="page-content">
    <div class="kb-details primary-page-header-left">What would you like to do?</div>
    <div>
      <div class="row">
        <div class="col-sm">
          <div class="kb-heading">Graph Management</div>
          <Collapse>
            <template v-slot:header> <h3>Add Graph to triple store</h3> </template>
            <template v-slot:content>
              <h3>URI Base: {{ uriBase }}</h3>
              <div>
                <v-select
                  :items="taskAreaOptions"
                  label="Select Task Area"
                  @input="showImportItems"
                  ref="taskArea"
                  @change="updateURI"
                  v-model="taskArea"
                ></v-select>
              </div>
              <div>
                <v-text-field
                  v-if="showTA3"
                  label="TA3 Team Run ID: {TeamID}-{RunID}"
                  v-model="ta3Run"
                  @change="updateURI"
                />
              </div>
              <div>
                <v-text-field
                  v-if="showTA2 || showTA3"
                  label="TA2 Team Run ID: {TeamID}-{RunID}"
                  v-model="ta2Run"
                  @change="updateURI"
                />
              </div>
              <div>
                <v-text-field
                  v-if="showTA2 || showTA3"
                  label="TA1 Team Run ID: {TeamID}-{RunID}"
                  v-model="ta1Run"
                  @change="updateURI"
                />
              </div>
              <v-file-input
                v-if="showTA2 || showTA3"
                accept="*.ttl"
                label="Select TTL File or ZIP/TAR/TGZ of TTLs (TTLs must be at the base of the compressed file)"
                show-size
                id="fileInput"
                ref="fileInput"
                @change="changeFileInput"
              ></v-file-input>
              <div>
                <v-text-field
                  v-if="showTA2 || showTA3"
                  label="URI of Graph"
                  v-model="newGraphURI"
                />
              </div>
              <div class="form-wrapper">
                <div class="row-wrapper-flex" v-if="showTA2 || showTA3">
                  <div>
                    <button class="dark-app-button" @click="importGraph()">Import TTL</button>
                  </div>
                  <div>
                    <button class="dark-app-button" @click="clearImportInputs()">Clear</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Import Status:</div>
                  <div><textarea v-model=importResponse readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header>
              <h3>Delete Graphs from triple store</h3>
            </template>
            <template v-slot:content>
              <radio-button-display
                :id="'delete-graphs'"
                :labels="radioButtonLabels"
                @radioButtonChange="toggleDataEntry($event)"
              >
              </radio-button-display>
              <v-select v-if="showMultiSelect"
                label='Select graphs to delete'
                :items="graphsList"
                v-model="graphsListDelete"
                id="selectDelete"
                ref="selectDelete"
                multiple>
              </v-select>
              <v-text-field v-else
                      label="Enter base URI of graphs to delete"
                      id="uriDelete"
                      ref="uriDelete"
                      v-model="graphsURIDelete"
              />
              <div class="form-wrapper">
                <div>
                  <button class="dark-app-button" @click="deleteGraph()">Delete Graph</button>
                </div>
                <div class="status-heading">
                  <div>Delete Status:</div>
                  <div><textarea v-model=deleteResponse rows="4" readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header>
              <h3>Rename Graph in triple store</h3>
            </template>
            <template v-slot:content> 
              <v-select 
                label='Select Graph to Rename' 
                ref="origRenameGraph"
                id="origRenameGraph"
                v-model="origRenameGraph"
                :items="graphsList"
                @change="defaultRenameGraph"
              ></v-select>
              <v-text-field  
                label="New Graph URI"
                id="newRenameGraph"
                ref="newRenameGraph"
                v-model="newRenameGraph"
              />
              <div class="form-wrapper">
                <div>
                <button class="dark-app-button" @click="renameGraph()">Rename Graph</button>
                </div>
                <div  class="status-heading">
                  <div>Rename Status:</div>
                  <div><textarea v-model=renameResponse readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
        </div>
        <!-- <div class="col-sm">
          <h3 class="kb-heading">Cache Management</h3>
            <Collapse>
              <template v-slot:header>
                <h3>Create TA2 Cache</h3>
              </template>
              <template v-slot:content> 
                <v-select 
                  label='Select Graphs to cache' 
                  v-model="cacheGraphList"
                  :items="rootTA2GraphsList"
                  @change="defaultRenameGraph"
                  multiple
                ></v-select>
                <div class="form-wrapper">
                  <div>
                    <button class="dark-app-button" @click="cacheTA2GraphsList()">Cache Graph(s)</button>
                  </div>
                  <div class="status-heading">
                    <div>Create Cache Status:</div>
                    <div><textarea v-model=createCacheResponse readonly></textarea></div>
                  </div>
                </div>
              </template>
            </Collapse>
            <Collapse>
              <template v-slot:header>
                <h3>Delete TA2 Indices From Cache</h3>
              </template>
              <template v-slot:content> 
                <v-select 
                  label='Select Graphs to delete indices' 
                  v-model="uncacheGraphList"
                  :items="rootTA2GraphsList"
                  @change="defaultRenameGraph"
                  multiple
                ></v-select>
                <div class="form-wrapper">
                  <div class="row-wrapper-flex">
                    <div>
                      <button class="dark-app-button" @click="deleteCacheList()">Clear selected indices</button>
                    </div>
                    <div>
                      <button class="dark-app-button" @click="deleteCacheAll()">Clear all indices</button>
                    </div>
                  </div>
                  <div class="status-heading">
                    <div>Clear Cache Status:</div>
                    <div><textarea v-model=clearCacheResponse readonly></textarea></div>
                  </div>
                </div>
              </template>
            </Collapse>
            <Collapse>
              <template v-slot:header>
                <h3>Create TA3 Cache</h3>
              </template>
              <template v-slot:content>
                <div class="form-wrapper">
                  <div>
                    <button class="dark-app-button" @click="cacheTA3Graphs()">Index All TA3 Graphs</button>
                  </div>
                  <div class="status-heading">
                    <div>Clear Cache Status:</div>
                    <div><textarea v-model=cacheTA3Response readonly></textarea></div>
                  </div>
                </div>
              </template>
            </Collapse>        
          </div>-->
        </div> 
    </div>
  </div>
</template>

<script>
import Collapse from '@/components/Collapse';
import RadioButtonDisplay from '@/components/RadioButtonDisplay';
import API from '../../api';
import { BASEURI } from '../../utils';

export default {
  name: 'GraphManagement',
  components: { Collapse, RadioButtonDisplay },
  data: () => ({
    taskAreaOptions: [
      { value: 'TA2', text: 'TA2' },
      { value: 'TA3', text: 'TA3' },
    ],
    showTA2: false,
    showTA3: false,
    showMultiSelect: false,
    radioButtonLabels: ['Delete graphs by base URI', 'Delete graphs by selection'],
    uriBase: BASEURI,
    newGraphURI: '',
    taskArea: '',
    ta1Run: '',
    ta2Run: '',
    ta3Run: '',
    importResponse: '',
    deleteResponse: '',
    renameResponse: '',
    clearCacheResponse: '',
    createCacheResponse: '',
    cacheTA3Response: '',
    uploadFile: undefined,
    graphsList: [],
    graphsListDelete: [],
    graphsURIDelete: '',
    cacheGraphList: [],
    uncacheGraphList: [],
    origRenameGraph: '',
    newRenameGraph: '',
    rootGraphsList: [],
    rootTA2GraphsList: [],
    rootTA3GraphsList: [],
  }),
  mounted() {
    this.updateGraphsList();
  },
  methods: {
    updateURI() {
      if (this.showTA2) {
        this.newGraphURI = [this.uriBase,this.taskArea,this.ta2Run,this.ta1Run].join('/');
      }
      if (this.showTA3) {
        this.newGraphURI = [this.uriBase,this.taskArea,this.ta3Run,this.ta2Run,this.ta1Run,(typeof this.uploadFile == 'undefined' ? '' :  (typeof this.uploadFile.name == 'undefined' ? '' : this.uploadFile.name.replace('.ttl', '')))].join('/');
      }
    },
    changeFileInput(file) {
      this.uploadFile = file;
      this.updateURI();
    },    
    clearImportInputs() {
      this.taskArea = '';
      this.ta3Run = '';
      this.ta2Run = '';
      this.ta1Run = '';
      this.uploadFile = undefined;
      this.$refs['fileInput'].reset();
      this.newGraphURI = '';
      this.showTA2 = false;
      this.showTA3 = false;
    },
    showImportItems(value) {
      if (value === 'TA2') {
        this.showTA2 = true;
        this.showTA3 = false;
      }
      if (value === 'TA3') {
        this.showTA2 = false;
        this.showTA3 = true;
      }
    },
    importGraph() {
      
      if (this.validateURI(this.newGraphURI)) {
        if (this.uploadFile.name.toLowerCase().endsWith('.ttl')) {
          this.importResponse = '> Currently importing ' + this.newGraphURI + '...\n';
          API.importGraph(this.newGraphURI, this.uploadFile).then((response) => {
            if (response.data && Object.keys(response.data).length > 0) {
              this.importResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
            }
            this.updateGraphsList();
            this.importResponse += '> Completed importing of ' + this.newGraphURI + '.\n';
            if (this.showTA2) {
              this.cacheTA2Graph(this.getGraphRoot(this.newGraphURI));
            }
          });
        }

        if (this.uploadFile.name.toLowerCase().endsWith('.zip') || 
          this.uploadFile.name.toLowerCase().endsWith('.tar') || 
          this.uploadFile.name.toLowerCase().endsWith('.tgz') || 
          this.uploadFile.name.toLowerCase().endsWith('.gz')) {
          this.importResponse = '> Currently importing ' + this.newGraphURI + '...\n';

          let graphbase = [this.uriBase,this.taskArea,this.ta3Run,this.ta2Run,this.ta1Run].join('/');

          API.importBatchGraph(graphbase, this.uploadFile).then((response) => {
              if (response.data && Object.keys(response.data).length > 0) {
                Object.keys(response.data).forEach(responseObj => {
                  this.importResponse += '> ' + response.data[responseObj].message + '\n';
                });
              }
            this.updateGraphsList();
            this.importResponse += '> Completed importing of ' + this.newGraphURI + '.\n';
            if (this.showTA2) {
              this.cacheTA2Graph(this.getGraphRoot(this.newGraphURI));
            }
          });
        }        
      } else {
        alert('Please complete all the components of the URI. File must be TTL, ZIP, TAR, TGZ, or GZ');
      }
    },
    renameGraph() {
      this.renameResponse = '> Currently renaming ' + this.origRenameGraph + ' to ' + this.newRenameGraph + '...\n';
      API.renameGraph(this.origRenameGraph, this.newRenameGraph).then((response) => {
        if (response.data && Object.keys(response.data).length > 0) {
          this.renameResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
        }
        this.updateGraphsList();
        this.renameResponse += '> Completed renaming of ' + this.origRenameGraph + ' to ' + this.newRenameGraph + '.\n';
        this.newRenameGraph = '';
      });
    },
    defaultRenameGraph() {
      this.newRenameGraph = this.origRenameGraph;
    },    
    updateGraphsList() {
      this.rootGraphsList = [];
      this.rootTA2GraphsList = [];
      this.rootTA3GraphsList = [];
      API.getGraphs().then((response) => {
        if (response.data && Object.keys(response.data).length > 0) {
          this.graphsList = response.data;
          this.graphsList.forEach(graph => {
            if (this.rootGraphsList.indexOf(graph) === -1) {
              this.rootGraphsList.push(graph);
            }

            if (graph.includes('/TA2/')) {
              if (this.rootTA2GraphsList.indexOf(graph) === -1)
                this.rootTA2GraphsList.push(graph);
            }
            if (graph.includes('/TA3/')) {
              if (this.rootTA3GraphsList.indexOf(this.getGraphRoot(graph)) === -1)
                this.rootTA3GraphsList.push(this.getGraphRoot(graph));
            }
          });
        }
      });
    },
    deleteGraph() {
      this.deleteResponse = '';

      if(this.graphsURIDelete) {
        this.deleteResponse += '> Currently deleting graphs containing the base URI ' + this.graphsURIDelete + '...\n';
        API.deleteBatchGraph(this.graphsURIDelete).then((response) => {
          if (response.data && Object.keys(response.data).length > 0) {
            for(const d of response.data) {
              this.deleteResponse += '> ' + JSON.parse(JSON.stringify(d)).message + '\n';
            }
          }
          this.deleteResponse += '> Completed deletion of graphs containing this base URI.\n';
        });
        this.graphsListDelete = '';
        this.$refs['uriDelete'].reset();
      }
      else if(this.graphsListDelete.length > 0) {
        this.graphsListDelete.forEach(graph => {
          if (graph.includes('/TA2/')) {
            this.deleteResponse += '> Currently deleting ' + graph + '...\n';
            API.deleteGraph(graph).then((response) => {
              if (response.data && Object.keys(response.data).length > 0) {
                this.deleteResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
              }
              this.updateGraphsList();
              this.deleteResponse += '> Completed deletion of ' + graph + ' .\n'
              this.deleteCacheGraph(graph);
              this.deleteResponse += '> Cache for ' + graph + ' was also removed.\n'
            });
          }

          if (graph.includes('/TA3/')) {
            this.deleteResponse += '> Currently deleting ' + graph + '...\n';
            API.deleteGraph(graph).then((response) => {
              if (response.data && Object.keys(response.data).length > 0) {
                this.deleteResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
              }
              this.updateGraphsList();
              this.deleteResponse += '> Completed deletion of ' + graph + ' .\n'
            });
          }

        });
        //needs check
        this.graphsListDelete = [];
        this.$refs['selectDelete'].reset();
      }
    },
    deleteCacheList() {
      this.clearCacheResponse = '';
      this.uncacheGraphList.forEach(graph => {
        this.deleteCacheGraph(graph);
      });

    },
    deleteCacheGraph(graph) {
      this.clearCacheResponse += '> Clearing index for ' + graph + '...\n';
      API.deleteCache(graph).then((response) => {
        if (response.data && Object.keys(response.data).length > 0) {
          this.clearCacheResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
        }
        //TODO: Catch Error
        this.updateGraphsList();
        this.clearCacheResponse += '> Clearing index for ' + graph + ' completed\n';
      });
    },
    deleteCacheAll() {
      if (confirm('Are you sure you want to delete all indices in the cache?')) {
        this.clearCacheResponse = '> Clearing all indices from cache...\n';
        API.deleteCache(null).then((response) => {
          if (response.data && Object.keys(response.data).length > 0) {
            this.clearCacheResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
          }
          //TODO: Catch Error
          this.updateGraphsList();
          this.clearCacheResponse += '> Clearing all indices has completed\n'
        });
      }
    },
    cacheTA2GraphsList() {
      this.createCacheResponse = '';
      this.cacheGraphList.forEach(graph => {

        var payload = {
          'taskArea': 'TA2',
          'overwrite': true,
          'graphRootURI': [graph]
        };

        this.createCacheResponse += '> Currently caching ' + graph + '...\n';

        API.cacheGraph(JSON.stringify(payload)).then((response) => {
          if (response.data && Object.keys(response.data).length > 0) {
            this.createCacheResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
          }
          //TODO: Catch Error
          this.createCacheResponse += '> Completed caching of ' + graph + ' .\n'
        });
      });
    },

    cacheTA2Graph(graph) {

      var payload = {
        'taskArea': 'TA2',
        'overwrite': true,
        'graphRootURI': [graph]
      };

      this.importResponse += '> Currently caching ' + graph + '...\n';

      API.cacheGraph(JSON.stringify(payload)).then((response) => {
        if (response.data && Object.keys(response.data).length > 0) {
          this.importResponse += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
        }
        //TODO: Catch Error
        this.importResponse += '> Completed caching of ' + graph + ' .\n';
      });
    },
    cacheTA3Graphs() {

      var payload = {
        'taskArea': 'TA3',
        'overwrite': true,
        'graphRootURI': this.rootTA3GraphsList
      };

      this.cacheTA3Response = '> Currently caching all TA3 Graphs...\n';

      API.cacheGraph(JSON.stringify(payload)).then((response) => {
        if (response.data && Object.keys(response.data).length > 0) {
          this.cacheTA3Response += '> ' + JSON.parse(JSON.stringify(response.data)).message + '\n';
        }
        //TODO: Catch Error
        this.cacheTA3Response += '> Completed caching of all TA3 Graphs.\n';
      });
    },

    getGraphRoot(graph) {
      if (graph.includes('/TA2/'))
        return graph;
      if (graph.includes('/TA3/'))
        return graph.replace('/' + graph.split('/').pop() , '');
    },
    validateURI(strURI) {
      if (strURI.includes('/TA2/')) {
        if (this.ta2Run !== '' &&
            this.ta1Run !== '' &&
            (typeof this.uploadFile !== 'undefined'))
              return true;
      }
      if (strURI.includes('/TA3/')) {
        if (this.ta3Run !== '' &&
            this.ta2Run !== '' &&
            this.ta1Run !== '' &&
            (typeof this.uploadFile !== 'undefined'))
            return true;
      }
      return false;
    },
    toggleDataEntry(value) {
      this.showMultiSelect = value !== this.radioButtonLabels[0];
    }
  },
};
</script>

<style lang="scss" scoped>
textarea {
  border: none !important;
  font-size: 8pt;
  font-family: Arial;
  background-color: #f3f3f3;
  color: #03777d;
  width: 100%;
}

.status-heading {
  font-size: 14px;
  width: 100%;
  margin-top: 26px;
}
</style>
