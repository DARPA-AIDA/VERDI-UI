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
  -->
<!--
 - The following vue component was designed for the StartTA3.vue file. An
 - example can be found in the UI here http://localhost:8081/#/test
 -->

<template>
    <div>
        <span :class="disabled ? 'disabled' : 'clickable'" @click="toggle">
            <v-icon v-if="open">mdi-minus-box-outline</v-icon>
            <v-icon v-else>mdi-plus-box-outline</v-icon>
            <span>{{ job }}</span>
        </span>
        <v-icon v-on:click="removeJob()">mdi-trash-can-outline</v-icon>
        <div class=details v-if="open">
            <div>{{ details }}</div>
        </div>
    </div>
</template>

<script>
    import {evalClient} from '../api'
    export default {
        data: () => ({
            open: false,
            details: {},
            futurePoll: null,
            currentPoll: null,
            disabled: false
        }),
        props: {
            job: {
                type: String,
                required: true
            }
        },
        methods: {
            removeJob() {
                this.open = false;
                this.disabled = true;
                evalClient.delete('job/' + this.job)
                .then(() => {
                    this.$emit('removed', {job: this.job})
                }).catch(err => {
                    this.$emit('removed', {err: 'Error during removal: ' + JSON.stringify(err)});
                });
            },
            toggle() {
                if (!this.disabled) {
                    this.open = !this.open;
                    if (this.open) {
                        this.startPolling();
                    } else {
                        this.removeFuturePoll();
                    }
                }
            },
            startPolling() {
                this.removeFuturePoll();
                if (!this.currentPoll) {
                    this.currentPoll = evalClient.get('job/' + this.job)
                        .then(res => this.details = res.data)
                        .catch(err => this.details = err.response ? err.response.status : err.message + '. Please contact the aministrator')
                        .finally(() => {
                            this.currentPoll = null;
                            if (this.open) {
                                this.futurePoll = setTimeout(this.startPolling, 1000);
                            }
                        });
                }
            },
            removeFuturePoll() {
                if (this.futurePoll !== null) {
                    clearTimeout(this.futurePoll);
                    this.futurePoll = null;
                }
            }
        },
        beforeDestroy() {
            this.open = false;
            this.removeFuturePoll();
        }
    }
</script>

<style scoped>
    .details {
        margin-left: 30px;
    }
    .clickable {
        cursor: pointer;
    }
    .disabled {
        color: #9e9e9e
    }
</style>
