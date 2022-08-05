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

<template>
    <div class="page-content">
        
        <div class="back-button-content" v-if="historyExists">
            <input type="button" value="BACK TO CLAIM" onclick="history.back()" class="dark-teal-submit-button">
        </div>
        <div class="back-button-content" v-if="!historyExists">
            <input type="button" value="CLOSE WINDOW" onclick="window.close()" class="dark-teal-submit-button">
        </div>        
        <div v-html="rawHTML"></div>
    </div>
</template>


<script>
import { s3Client } from '../../api'
    export default {
        name: 'Document',
        data: () => ({
            rawHTML: '',
            historyExists: false
        }),
        methods: {
            async getContent(docId) {
                const clean = docId.replace(/[^a-zA-Z0-9]/, '');
                try {
                    this.rawHTML = (await s3Client.get(clean + '.html')).data;
                } catch (e) {
                    this.rawHTML = JSON.stringify(e);
                }
            },
            updateHistoryExists() {
                //console.warn(window.history);
                if (window.history.length > 1) {
                    this.historyExists = true;
                } else {
                    this.historyExists = false;
                }
                //console.warn(this.historyExists);
            }            
        },
        created() {
            this.getContent(this.$route.params.id);
        },
        mounted() {
            this.updateHistoryExists();
        },        
        //TODO: should changes to parameters be pushed to route history?
        beforeRouteUpdate(to, from, next) {
            this.getContent(to.params.id);
            next();
        }
    }

</script>

<style scoped>
.back-button-content {
    color: white;
    padding: 20px 0 20px 0;
    width: 100%;
    text-align: right;
}
</style>
