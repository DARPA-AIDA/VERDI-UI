<template>
  <div class="page-content">
    <div class="qcm-details primary-page-header-left">What would you like to do?</div>
    <div>
      <div class="row">
        <div class="col-sm">
          <div class="qcm-heading">Query Claim Manager</div>
          <Collapse>
            <template v-slot:header> <h3>Create Query Claim List</h3> </template>
            <template v-slot:content>
              <div>
                <v-text-field
                  label="Query Claim List name"
                  v-model="queryClaimListCreateName"
                />
              </div>
              <div>
                  <button class="dark-app-button" @click="createQueryClaimList()">Get Query Claim List</button>
              </div>
              <div>
                <div class="status-heading">
                  <div>Query Claim List Id:</div>
                  <div><textarea v-model=createdListResp readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header> <h3>Rename Query Claim List</h3> </template>
            <template v-slot:content>
              <div>
                <v-select v-model="selectedListRename"
                  :items="listDropDownData.options"
                  item-text="text"
                  label="Query Claim List to rename."
                  return-object
                ></v-select>
                <span>Selected: {{ selectedListRename.name }} </span>
              </div>
              <div>
                <v-text-field
                  label="New name for the selected Query Claim List."
                  v-model="queryClaimNameChange"
                />
              </div>
              <div class="form-wrapper">
                <div class="row-wrapper-flex">
                  <div>
                    <button class="dark-app-button" @click="renameQueryClaimList()">Rename query claim list</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Rename Status:</div>
                  <div><textarea v-model=renameListResp readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header> <h3>Add entry to Query Claim List</h3> </template>
            <template v-slot:content>
              <div>
                <v-select v-model="selectedListAddEntry"
                  :items="listDropDownData.options"
                  item-text="text"
                  label="Query Claim List to add entries to."
                  return-object
                  @change="getAddDropDownData()"
                ></v-select>
                <span>Selected: {{ selectedListAddEntry.name }} </span>
              </div>
              <div>
                <v-select v-model="queryClaimIdsAdd"
                  :items="addDropDownData.options"
                  item-text="text"
                  label="Query Claims to add."
                  multiple
                ></v-select>
              </div>
              <div class="form-wrapper">
                <div class="row-wrapper-flex">
                  <div>
                    <button class="dark-app-button" @click="addEntryToQueryClaimList()">Add Entries</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Addition Status:</div>
                  <div><textarea v-model=addedListResp readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header>
              <h3>Remove Entry to Query Claim List</h3>
            </template>
            <template v-slot:content>
              <div>
                <v-select v-model="selectedListRemoveEntry"
                  :items="listDropDownData.options"
                  item-text="text"
                  label="Query Claim List to remove entries from."
                  return-object
                  @change="getRemovalDropDownData()"
                ></v-select>
                <span>Selected: {{ selectedListRemoveEntry.name }} </span>
              </div>
              <div>
                <v-select v-model="queryClaimIdsRemove"
                  :items="removeDropDownData.options"
                  item-text="text"
                  label="Query Claims to remove."
                  multiple
                ></v-select>
              </div>
              <div class="form-wrapper">
                <div class="row-wrapper-flex">
                  <div>
                    <button class="dark-app-button" @click="removeEntryFromQueryClaimList()">Remove Entries</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Removal Status:</div>
                  <div><textarea v-model=removedListResp readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header>
              <h3>Delete Query Claim List</h3>
            </template>
            <template v-slot:content>
              <v-select v-model="selectedListDelete"
                  :items="listDropDownData.options"
                  item-text="text"
                  label="Query Claim List to delete."
                  return-object
                ></v-select>
                <span>Selected: {{ selectedListDelete.name }} </span>
              <div class="form-wrapper">
                <div>
                <button class="dark-app-button" @click="deleteQueryClaimList()">Delete Query Claim List</button>
                </div>
                <div  class="status-heading">
                  <div><textarea v-model=deleteQueryClaimListResp readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header> <h3>Create Query Claim</h3> </template>
            <template v-slot:content>
              <div>
                <v-text-field
                  label="Topic"
                  v-model="topicCreate"
                />
              </div>
              <div>
                <v-text-field
                  label="Subtopic"
                  v-model="subtopicCreate"
                />
              </div>
              <div>
                <v-text-field
                  label="Claim Template"
                  v-model="claimTemplateCreate"
                />
              </div>
              <div>
                <v-text-field
                  label="X Variable"
                  v-model="xVarCreate"
                />
              </div>
              <v-select v-model="selectedLangCreate"
                  :items="languageDropDown.options"
                  item-text="text"
                  label="Query Claim Language"
                  return-object
                ></v-select>
                <span>Selected: {{ languageDropDown.value }} </span>
              <div class="form-wrapper">
                <div class="row-wrapper-flex">
                  <div>
                    <button class="dark-app-button" @click="createQueryClaim()">Create</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Create Status:</div>
                  <div><textarea v-model=createQueryClaimResponse readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header>
              <h3>Edit Query Claim</h3>
            </template>
            <template v-slot:content>
              <v-select v-model="selectedIdEdit"
                  :items="idDropDownData.options"
                  item-text="text"
                  label="Query Claim to edit."
                  return-object
                ></v-select>
                <span>Selected: {{ selectedIdEdit }} </span>
              <div>
                <v-text-field
                  label="Topic"
                  v-model="topicEdit"
                />
              </div>
              <div>
                <v-text-field
                  label="Subtopic"
                  v-model="subtopicEdit"
                />
              </div>
              <div>
                <v-text-field
                  label="Claim Template"
                  v-model="claimTemplateEdit"
                />
              </div>
              <div class="form-wrapper">
                <div class="row-wrapper-flex">
                  <div>
                    <button class="dark-app-button" @click="editQueryClaim()">Import TTL</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Edit Status:</div>
                  <div><textarea v-model=editIdResponse readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header>
              <h3>Delete Query Claim</h3>
            </template>
            <template v-slot:content>
              <div>
                <v-select v-model="selectedIdDelete"
                  :items="idDropDownData.options"
                  item-text="text"
                  label="Query Claim List to rename."
                  return-object
                ></v-select>
              </div>
              <div class="form-wrapper">
                <div>
                <button class="dark-app-button" @click="deleteQueryClaim()">Delete</button>
                </div>
                <div  class="status-heading">
                  <div>Delete Status:</div>
                  <div><textarea v-model=deleteResponse readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
          <Collapse>
            <template v-slot:header> <h3>Add Language for Query Claim</h3> </template>
            <template v-slot:content>
              <div>
                <v-text-field
                  label="Language to add"
                  v-model="langAdd"
                />
              </div>
              <div>
                <v-text-field
                  label="Confirm Language"
                  v-model="langAddConfirm"
                  return-object
                  @change="checkMatching()"
                />
              </div>
              <p style="color:red;"> 
                {{ languageAddWarning.toString() }}
              </p>
              <div class="form-wrapper">
                <div class="row-wrapper-flex">
                  <div>
                    <button class="dark-app-button" @click="addLanguage()">Add</button>
                  </div>
                </div>
                <div class="status-heading">
                  <div>Create Status:</div>
                  <div><textarea v-model=addedLanguageResponse readonly></textarea></div>
                </div>
              </div>
            </template>
          </Collapse>
        </div>
        </div>
    </div>
</div>
</template>

<script>
import Collapse from '@/components/Collapse';
import API from '../../api';
import { BASEURI } from '../../utils';

export default {
  name: 'GraphManagement',
  components: { Collapse },
  data: () => ({
    uriBase: BASEURI,
    langAdd: '',
    langAddConfirm: '',
    languageAddWarning: '',
    topicEdit: '',
    subtopicEdit: '',
    claimTemplateEdit: '',
    languageEdit: '',
    languageEditSelected: '',
    selectedLangCreate: '',
    xVarEdit: '',
    queryClaimListCreateName: '',
    topicCreate: '',
    subtopicCreate: '',
    claimTemplateCreate: '',
    xVarCreate: '',
    createQueryClaimResponse: '',
    renameListResp: '',
    editIdResponse: '',
    createdListResp: '',
    queryClaimIdsAdd: '',
    addedListResp: '',
    queryClaimIdsRemove: '',
    removedListResp: '',
    deleteQueryClaimListResp: '',
    queryClaimNameChange: '',
    deleteResponse: '',
    renameResponse: '',
    clearCacheResponse: '',
    createCacheResponse: '',
    cacheTA3Response: '',
    importResponse: '',
    selectedAddEntries: '',
    selectedListRename: '',
    selectedListAddEntry: '',
    selectedListRemoveEntry: '',
    selectedListDelete: '',
    selectedIdEdit: '',
    addedLanguageResponse: '',
    selectedIdDelete: '',
    listDropDownData: {
      options: [
        { text: 'Please select a query claim list', value: '', id:'', name:'' },
      ]
    },
    idDropDownData: {
      options: [
        { text: 'Please select a query claim', value: '', id:'', name:'' },
      ]
    },
    removeDropDownData: {
      options: [
        { text: 'Please select a query claim to remove', value: '', id:'', name:'' },
      ]
    },
    addDropDownData: {
      options: [
        { text: 'Please select a query claim to add', value: '', id:'', name:'' },
      ]
    },
    languageDropDown: {
      options: [
        { text: 'Please select a language', value: '' },
      ]
    }
  }),
  mounted() {
    this.getListDropDownData();
    this.getIdDropDownData();
    this.getLanguages();
  },
  methods: {
    createQueryClaimList() {
      let request = '{ "name": "' + this.queryClaimListCreateName  + '", "ids": [ "" ] }';
      API.createQueryClaimList(request).then(response => {
        this.createdListResp = 'Query claim list "' + response.data.message + '" created successfully!'
      })

      this.getListDropDownData();
      this.getIdDropDownData();
    },
    addEntryToQueryClaimList() {
      let request = '{ "queryClaimListId": "' + this.selectedListAddEntry.id.trim()
       + '", "name":"' + this.selectedListAddEntry.name + '",'
       + '"queryClaims": [ {$idsToAdd} ] }'
      let idList = this.queryClaimIdsAdd
      let repopList = ''

      idList.forEach(idToAdd => {
        repopList = repopList.concat('"')
        repopList = repopList.concat(idToAdd.trim())
        repopList = repopList.concat('",')
      });

      repopList = repopList.slice(0, repopList.length - 1);
      request = request.replace('{$idsToAdd}', repopList);

      API.queryClaimListAddEntry(request).then(response => {
        this.addedListResp = 'Entries successfully added to Query Claim List ' + response.data.message;
      })

      this.getListDropDownData();
      this.getIdDropDownData();
    },
    removeEntryFromQueryClaimList() {
      let request = '{ "queryClaimListId": "' + this.selectedListRemoveEntry.id.trim()
      + '", "name":"' + this.selectedListRemoveEntry.name + '",'
      + ' "queryClaims": [ {$idsToAdd} ] }'

      let idList = this.queryClaimIdsRemove
      let repopList = ''

      idList.forEach(idToAdd => {
        repopList = repopList.concat('"')
        repopList = repopList.concat(idToAdd.trim())
        repopList = repopList.concat('",')
      });

      repopList = repopList.slice(0, repopList.length - 1)
      request = request.replace('{$idsToAdd}', repopList);


      API.queryClaimListRemoveEntry(request).then(response => {
        this.removedListResp = 'Entries removed from query claim list ' + response.data.message;
      })

      this.getListDropDownData();
      this.getIdDropDownData();
    },
    renameQueryClaimList() {
      this.renameListResp = '';

      let request = '{ '
      request = request.concat('"queryId": "' + this.selectedListRename.id + '", ')
      request = request.concat('"name": "' + this.queryClaimNameChange + '" }');

      API.renameQueryClaimList(request).then((response) => {
        this.renameListResp = response.data.message
        this.selectedListRename.name = this.queryClaimNameChange
        this.selectedListRename.value = this.queryClaimNameChange
      })

      this.getListDropDownData();
      this.getIdDropDownData();
    },
    deleteQueryClaimList() {
      let request = '{ "id": "' + this.selectedListDelete.toString().trim() + '" }'

      API.deleteQueryClaimList(request).then(() => {
        this.deleteQueryClaimListResp = 'Query claim list deleted.';
      }).then(this.getListDropDownData())
    },
    createQueryClaim() {
      let request = '{'
      request = request.concat('"topic": "' + this.topicCreate.toString().trim() + '",');
      request = request.concat('"subtopic": "' + this.subtopicCreate.toString().trim() + '",');
      request = request.concat('"claimTemplate": "' + this.claimTemplateCreate.toString().trim());
      request = request.concat('"language": "' + this.selectedLangCreate.toString().trim());
      request = request.concat('"xVariable": "' + this.xVarCreate.toString().trim());
      request = request.concat('"}')

      API.createQueryClaim(request).then((response) => {
        this.createQueryClaimResponse = response.data.message + ' created.';
      })
    },
    deleteQueryClaim() {
      let request = '{ "id": "' + this.selectedIdDelete.value + '" }'

      API.deleteQueryClaim(request).then((response) => {
        this.deleteResponse = response.data.message + ' has been deleted successfully.'
      })

      this.getListDropDownData();
      this.getIdDropDownData();
    },
    editQueryClaim() {
      let request = '{' +
        '"queryId": "' + this.selectedIdEdit.value + '", ' +
        '"topic": "' + this.topicEdit + '", ' +
        '"subtopic": "' + this.subtopicEdit + '", ' +
        '"claimTemplate": "' + this.claimTemplateEdit +
        '"language": "' + this.languageEdit +
        '"xVariable": "' + this.xVarEdit
        '" }'

      API.editQueryClaim(request).then((response) => {
        this.editIdResponse = response.data.message + ' has been editted successfully.'
      })

      this.getListDropDownData();
      this.getIdDropDownData();
    },
    getListDropDownData() {
      this.listDropDownData.options = []
      this.listDropDownData.options.push( { text: 'Please select a query claim list', value: '', id:'', name:'' } )
      API.getQueryClaimListInfo().then((response) => {
          var queryClaimMap = new Map();

          response.data.forEach(queryClaimRecord => {
            queryClaimMap.set(queryClaimRecord.id, queryClaimRecord.name)
          });

          for (let [key, value] of queryClaimMap) {
            this.listDropDownData.options.push({
              text: key,
              value: ((value === '') ? 'UNNAMED' : value),
              id:key,
              name: value,
              hasName: ((value === '') ? false : true) })
          }
      })
    },
    getIdDropDownData() {
      this.idDropDownData.options = []
      this.idDropDownData.options.push( { text: 'Please select a query claim', value: '', id:'', name:'' } )
      API.getAllQueryClaims().then((response) => {
          response.data.forEach(queryClaimRecord => {
            this.idDropDownData.options.push({
              text: queryClaimRecord.queryId,
              value: queryClaimRecord.queryId,
              claimTemplate: queryClaimRecord.claimTemplate,
              queryId: queryClaimRecord.queryId,
              subtopic: queryClaimRecord.subtopic,
              topic: queryClaimRecord.topic,
              language: queryClaimRecord.language,
              xVariable: queryClaimRecord.xVariable
            })
          });
      })
    },
    getRemovalDropDownData() {
      this.removeDropDownData.options = []
      this.removeDropDownData.options.push({ text: 'Please select a query claim to remove', value: '', id:'', name:'' })

      API.getQueryClaimList(this.selectedListRemoveEntry.id).then((response) => {
        if(response.data.ids.length == 0) { return }

        response.data.ids.forEach(queryClaimRecord => {
          this.removeDropDownData.options.push({
            text: queryClaimRecord,
            value: queryClaimRecord,
            id: queryClaimRecord
            })
        });
      })

    },
    getAddDropDownData() {
      this.addDropDownData.options = []

      var entriesAssocatedToQueryClaim = [];
      var allEntries = this.idDropDownData.options;

      API.getQueryClaimList(this.selectedListAddEntry.id).then((response) => {
        response.data.ids.forEach(queryClaimRecord => {
          entriesAssocatedToQueryClaim.push({
            text: queryClaimRecord,
            value: queryClaimRecord,
            id: queryClaimRecord
          })
        });

        this.addCorrectEntries(allEntries, entriesAssocatedToQueryClaim);

      })
    },
    addCorrectEntries(allEntries, entriesAssocatedToQueryClaim) {

      var entriesToHaveInDropDown = [];
      entriesToHaveInDropDown.push( { text: 'Please select a query claim.', value: '', id:'', name:'' } )

      var entryfound = false;

      for(var i = 0; i < allEntries.length; i++) {
        entryfound = false
        for(var j = 0; j < entriesAssocatedToQueryClaim.length; j++) {
          if(allEntries[i].text == entriesAssocatedToQueryClaim[j].text) {
            entryfound = true;
            break;
          } else if(allEntries[i].text.toString().includes('Please select a query claim')) {
            entryfound = true;
            break;
          }
        }
        if(!entryfound) {
          entriesToHaveInDropDown.push(allEntries[i])
        }
      }

      this.addDropDownData.options = entriesToHaveInDropDown;
    },
    getLanguages() {
      API.getLanguages().then((response) => {

        response.data.forEach(language => {
          this.languageDropDown.options.push({
            text: language.lang,
            value: language.lang
          })

        })
      })
    },
    checkMatching() {
      if(this.langAddConfirm == this.langAdd && this.langAddConfirm != '') {
        this.languageAddWarning = '';
      } else if(this.langAddConfirm != this.langAdd && this.langAddConfirm != '') {
        this.languageAddWarning = 'Language to add doesn\'t match';
      }
    },
    addLanguage() {
      if(this.languageAddWarning == '' && this.langAddConfirm != '' ) {
        API.addLanguage(this.langAddConfirm.trim().toString()).then(response => {
          this.addedLanguageResponse = response.data.message
        })
      }
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
