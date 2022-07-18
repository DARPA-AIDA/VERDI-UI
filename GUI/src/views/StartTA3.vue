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


<template>
    <div>
        <!-- Task Type -->
        <div>Task Type: <input v-model="task_type"></div>
        <!-- Algorithm -->
        <div>Algorithm: <input v-model="algorithm"></div>
        <!-- Input -->
        <div>Input: <input v-model="input_corpus"></div>
        <!-- TODO: all SIN references need to be removed or replaced -->
        <div>SIN ID: <input v-model="sin_id"></div>
        <!-- SIN base64 -->
        <div class="sin">SIN: {{ sin }}</div>
        <br/>
        <!-- button to job with above information -->
        <div><button v-on:click="submit" :disabled="isButtonDisabled">Submit</button></div>
        <br/>
        <!-- all job status -->
        <div><strong>Jobs:</strong></div>
        <!-- <div v-if="!formattedJobs">None</div>
        <div v-else> -->
            <eval-job v-for="job in formattedJobs" :key=job :job=job @removed="handleRemoved($event)"></eval-job>
        <!-- </div> -->
        <!-- all pod status -->
        <!-- <div>{{ pods }}</div> -->
        <br/>
        <!-- instance history for debugging purposes -->
        <div><strong>Debug:</strong></div>
        <div v-for="(line, index) in history" :key=index>{{ line }}</div>
    </div>
</template>

<script>
    import EvalJob from '../components/EvalJob.vue';
    import {evalClient} from '../api'
    export default {
        components: { EvalJob },
        name: 'StartTA3',
        data: () => ({
            isSubmitted: false,
            jobs: '',
            pods: '',
            polling: {},
            task_type: 'TA3',
            algorithm: 'UTexas',
            input_corpus: 'OPERA_TA1.OPERA_TA2',
            history: [],
            //TODO: remove or replace SIN references
            sin_id: 'E201',
            sin: 'PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPCFET0NUWVBFIGluZm9ybWF0aW9uX25lZWQgU1lTVEVNICIuL2R0ZC9pbmZvcm1hdGlvbl9uZWVkLmR0ZCI+Cgo8IS0tClN0YXRlbWVudCBvZiBpbmZvcm1hdGlvbiBuZWVkIGZvciB0b3BpYyBFMjAxCkRlYXRoIG9mIEh1Z28gQ2jDoXZlegoKVGhpcyBTSU4gZXhwZWN0cyByZXR1cm5lZCBoeXBvdGhlc2VzIHRvIGFuc3dlciB0aGUgZm9sbG93aW5nIG5hdHVyYWwgIApsYW5ndWFnZSBmYWNldC1sZXZlbCBxdWVyaWVzIChhbmQgcHJvdmlkZSBhZGRpdGlvbmFsIHJlbGV2YW50IGluZm9ybWF0aW9uKTogIAoKRTIwMV9RMDAxICAgIFdoZW4gZGlkIEh1Z28gQ2jDoXZleiBkaWU/CkUyMDFfUTAwMiAgICBXaGVyZSBkaWQgSHVnbyBDaMOhdmV6IGRpZT8KRTIwMV9RMDAzICAgIFdoYXQgY2F1c2VkIEh1Z28gQ2jDoXZleidzIGRlYXRoPwpFMjAxX1EwMDUgICAgV2hvIHdhcyBiZWhpbmQgdGhlIGtpbGxpbmcgb2YgSHVnbyBDaMOhdmV6PwpFMjAxX1EwMDYgICAgV2hvIGNhcnJpZWQgb3V0IHRoZSBraWxsaW5nIG9mIEh1Z28gQ2jDoXZlej8KRTIwMV9RMDA3ICAgIFdobyBhc3Npc3RlZCBpbiB0aGUga2lsbGluZyBvZiBIdWdvIENow6F2ZXo/CgpOLkIuOiBUaGUgU0lOIHByb3ZpZGVzIGEgc3RydWN0dXJlZCByZXByZXNlbnRhdGlvbiBvZiB0aGUgcXVlcmllcywgYnV0CmFuIGFuc3dlciB0byBhIHF1ZXJ5IG1heSBiZSBleHByZXNzZWQgaW4gYSBoeXBvdGhlc2lzIHVzaW5nIGV2ZW50CmFuZCByZWxhdGlvbiB0eXBlcyB0aGF0IGFyZSBkaWZmZXJlbnQgZnJvbSB0aG9zZSBpbiB0aGUgcXVlcnkuCgotLT4KCjxpbmZvcm1hdGlvbl9uZWVkIGlkPSJBSURBX00zNl9UQTNfRTIwMSI+CiAgPGZyYW1lcz4KICAgIDxmcmFtZSBpZD0iRjEiPiAgCiAgICAgIDxlZGdlcz4KCjwhLS0gRTIwMV9RMDAyICAgIFdoZXJlIGRpZCBIdWdvIENow6F2ZXogZGllPyAgLS0+CgogICAgICAgIDxlZGdlIGlkPSJBSURBX00zNl9UQTNfRTIwMV9GMV9FMSI+CiAgICAgICAgICA8c3ViamVjdD4gP0h1Z29DaGF2ZXpEZWF0aCA8L3N1YmplY3Q+CiAgICAgICAgICA8cHJlZGljYXRlPiBMaWZlLkRpZV9WaWN0aW0gPC9wcmVkaWNhdGU+CiAgICAgICAgICA8b2JqZWN0PiA/SHVnb0NoYXZleiA8L29iamVjdD4KICAgICAgICA8L2VkZ2U+CgogICAgICAgIDxlZGdlIGlkPSJBSURBX00zNl9UQTNfRTIwMV9GMV9FMiI+CiAgICAgICAgICA8c3ViamVjdD4gP0h1Z29DaGF2ZXpEZWF0aCA8L3N1YmplY3Q+CiAgICAgICAgICA8cHJlZGljYXRlPiBMaWZlLkRpZV9QbGFjZSA8L3ByZWRpY2F0ZT4KICAgICAgICAgIDxvYmplY3Q+ID9EZWF0aFBsYWNlIDwvb2JqZWN0PgogICAgICAgIDwvZWRnZT4KCjwhLS0gRTIwMV9RMDAzICAgIFdoYXQgY2F1c2VkIEh1Z28gQ2jDoXZleidzIGRlYXRoPyAgLS0+CgogICAgICAgIDxlZGdlIGlkPSJBSURBX00zNl9UQTNfRTIwMV9GMV9FMyI+CiAgICAgICAgICA8c3ViamVjdD4gP0h1Z29DaGF2ZXpEZWF0aCA8L3N1YmplY3Q+CiAgICAgICAgICA8cHJlZGljYXRlPiBMaWZlLkRpZV9NZWRpY2FsSXNzdWUgPC9wcmVkaWNhdGU+CiAgICAgICAgICA8b2JqZWN0PiA/RGVhdGhDYXVzZSA8L29iamVjdD4KICAgICAgICA8L2VkZ2U+Cgo8IS0tIEUyMDFfUTAwNSAgICBXaG8gd2FzIGJlaGluZCB0aGUga2lsbGluZyBvZiBIdWdvIENow6F2ZXo/ICAtLT4KCiAgICAgICAgPGVkZ2UgaWQ9IkFJREFfTTM2X1RBM19FMjAxX0YxX0U0Ij4KICAgICAgICAgIDxzdWJqZWN0PiA/RGVhdGhTcG9uc29yc2hpcCA8L3N1YmplY3Q+CiAgICAgICAgICA8cHJlZGljYXRlPiBHZW5lcmFsQWZmaWxpYXRpb24uU3BvbnNvcnNoaXBfQWN0b3JPckV2ZW50IDwvcHJlZGljYXRlPgogICAgICAgICAgPG9iamVjdD4gP0h1Z29DaGF2ZXpEZWF0aCA8L29iamVjdD4KICAgICAgICA8L2VkZ2U+CgogICAgICAgIDxlZGdlIGlkPSJBSURBX00zNl9UQTNfRTIwMV9GMV9FNSI+CiAgICAgICAgICA8c3ViamVjdD4gP0RlYXRoU3BvbnNvcnNoaXAgPC9zdWJqZWN0PgogICAgICAgICAgPHByZWRpY2F0ZT4gR2VuZXJhbEFmZmlsaWF0aW9uLlNwb25zb3JzaGlwX1Nwb25zb3IgPC9wcmVkaWNhdGU+CiAgICAgICAgICA8b2JqZWN0PiA/RGVhdGhTcG9uc29yIDwvb2JqZWN0PgogICAgICAgIDwvZWRnZT4KCQo8IS0tICBFMjAxX1EwMDYgICAgV2hvIGNhcnJpZWQgb3V0IHRoZSBraWxsaW5nIG9mIEh1Z28gQ2jDoXZlej8gIC0tPgoKICAgICAgICA8ZWRnZSBpZD0iQUlEQV9NMzZfVEEzX0UyMDFfRjFfRTYiPgogICAgICAgICAgPHN1YmplY3Q+ID9IdWdvQ2hhdmV6RGVhdGggPC9zdWJqZWN0PgogICAgICAgICAgPHByZWRpY2F0ZT4gTGlmZS5EaWVfS2lsbGVyIDwvcHJlZGljYXRlPgogICAgICAgICAgPG9iamVjdD4gP0RlYXRoS2lsbGVyIDwvb2JqZWN0PgogICAgICAgIDwvZWRnZT4KCjwhLS0gRTIwMV9RMDA3ICAgIFdobyBhc3Npc3RlZCBpbiB0aGUga2lsbGluZyBvZiBIdWdvIENow6F2ZXo/ICAtLT4KCiAgICAgICAgPGVkZ2UgaWQ9IkFJREFfTTM2X1RBM19FMjAxX0YxX0U3Ij4KICAgICAgICAgIDxzdWJqZWN0PiA/RGVhdGhBc3Npc3RhbmNlIDwvc3ViamVjdD4KICAgICAgICAgIDxwcmVkaWNhdGU+IEdlbmVyYWxBZmZpbGlhdGlvbi5TcG9uc29yc2hpcC5IZWxwU3VwcG9ydF9BY3Rvck9yRXZlbnQgPC9wcmVkaWNhdGU+CiAgICAgICAgICA8b2JqZWN0PiA/SHVnb0NoYXZlekRlYXRoIDwvb2JqZWN0PgogICAgICAgIDwvZWRnZT4KCiAgICAgICAgPGVkZ2UgaWQ9IkFJREFfTTM2X1RBM19FMjAxX0YxX0U4Ij4KICAgICAgICAgIDxzdWJqZWN0PiA/RGVhdGhBc3Npc3RhbmNlIDwvc3ViamVjdD4KICAgICAgICAgIDxwcmVkaWNhdGU+IEdlbmVyYWxBZmZpbGlhdGlvbi5TcG9uc29yc2hpcC5IZWxwU3VwcG9ydF9TcG9uc29yIDwvcHJlZGljYXRlPgogICAgICAgICAgPG9iamVjdD4gP0RlYXRoQXNzaXN0YW50IDwvb2JqZWN0PgogICAgICAgIDwvZWRnZT4KCQogICAgICA8L2VkZ2VzPgogICAgPC9mcmFtZT4gICAgICAKICA8L2ZyYW1lcz4KCgogIDx0ZW1wb3JhbF9pbmZvX2xpc3Q+Cgo8IS0tIEUyMDFfUTAwMSAgICBXaGVuIGRpZCBIdWdvIENow6F2ZXogZGllPyAgLS0+CiAgICA8dGVtcG9yYWxfaW5mbz4KICAgICAgPHN1YmplY3Q+ID9IdWdvQ2hhdmV6RGVhdGggPC9zdWJqZWN0PgogICAgICAgIDxzdGFydF90aW1lPgogICAgICAgICAgPHllYXI+P0h1Z29DaGF2ZXpEZWF0aFllYXI8L3llYXI+CiAgICAgICAgICA8bW9udGg+P0h1Z29DaGF2ZXpEZWF0aE1vbnRoPC9tb250aD4KICAgICAgICAgIDxkYXk+P0h1Z29DaGF2ZXpEZWF0aERheTwvZGF5PgogICAgICAgICAgPGhvdXI+PC9ob3VyPgogICAgICAgICAgPG1pbnV0ZT48L21pbnV0ZT4KICAgICAgICA8L3N0YXJ0X3RpbWU+CiAgICAgICAgPGVuZF90aW1lPgogICAgICAgICAgPHllYXI+P0h1Z29DaGF2ZXpEZWF0aFllYXI8L3llYXI+CiAgICAgICAgICA8bW9udGg+P0h1Z29DaGF2ZXpEZWF0aE1vbnRoPC9tb250aD4KICAgICAgICAgIDxkYXk+P0h1Z29DaGF2ZXpEZWF0aERheTwvZGF5PgogICAgICAgICAgPGhvdXI+PC9ob3VyPgogICAgICAgICAgPG1pbnV0ZT48L21pbnV0ZT4KICAgICAgICA8L2VuZF90aW1lPgogICAgPC90ZW1wb3JhbF9pbmZvPgoKICA8L3RlbXBvcmFsX2luZm9fbGlzdD4KCiAgPGVudHJ5cG9pbnRzPgoKPCEtLSAgSHVnb0NoYXZleiAtLT4gICAgCiAgIDxlbnRyeXBvaW50PgogICAgICA8bm9kZT4gID9IdWdvQ2hhdmV6IDwvbm9kZT4KICAgICAgPHR5cGVkX2Rlc2NyaXB0b3JzPgogICAgICAgIDx0eXBlZF9kZXNjcmlwdG9yPgogICAgICAgICAgPGVudHR5cGU+IFBFUiA8L2VudHR5cGU+CiAgICAgICAgICA8c3RyaW5nX2Rlc2NyaXB0b3I+CiAgICAgICAgICAgIDxuYW1lX3N0cmluZz4gSHVnbyBDaGF2ZXogPC9uYW1lX3N0cmluZz4KICAgICAgICAgIDwvc3RyaW5nX2Rlc2NyaXB0b3I+CiAgICAgICAgPC90eXBlZF9kZXNjcmlwdG9yPgogICAgICAgIDx0eXBlZF9kZXNjcmlwdG9yPgogICAgICAgICAgPGVudHR5cGU+IFBFUiA8L2VudHR5cGU+CiAgICAgICAgICA8c3RyaW5nX2Rlc2NyaXB0b3I+CiAgICAgICAgICAgIDxuYW1lX3N0cmluZz4gSHVnbyBDaMOhdmV6IDwvbmFtZV9zdHJpbmc+CiAgICAgICAgICA8L3N0cmluZ19kZXNjcmlwdG9yPgogICAgICAgIDwvdHlwZWRfZGVzY3JpcHRvcj4KICAgICAgICA8dHlwZWRfZGVzY3JpcHRvcj4KICAgICAgICAgIDxlbnR0eXBlPiBQRVIgPC9lbnR0eXBlPgogICAgICAgICAgPHN0cmluZ19kZXNjcmlwdG9yPgogICAgICAgICAgICA8bmFtZV9zdHJpbmc+IEh1Z28gUmFmYWVsIENow6F2ZXogRnLDrWFzIDwvbmFtZV9zdHJpbmc+CiAgICAgICAgICA8L3N0cmluZ19kZXNjcmlwdG9yPgogICAgICAgIDwvdHlwZWRfZGVzY3JpcHRvcj4KICAgICAgICA8dHlwZWRfZGVzY3JpcHRvcj4KICAgICAgICAgIDxlbnR0eXBlPiBQRVIgPC9lbnR0eXBlPgogICAgICAgICAgPHN0cmluZ19kZXNjcmlwdG9yPgogICAgICAgICAgICA8bmFtZV9zdHJpbmc+IEh1Z28gUmFmYWVsIENoYXZleiBGcmlhcyA8L25hbWVfc3RyaW5nPgogICAgICAgICAgPC9zdHJpbmdfZGVzY3JpcHRvcj4KICAgICAgICA8L3R5cGVkX2Rlc2NyaXB0b3I+CiAgICAgICAgPHR5cGVkX2Rlc2NyaXB0b3I+CiAgICAgICAgICA8ZW50dHlwZT4gUEVSIDwvZW50dHlwZT4KICAgICAgICAgIDxzdHJpbmdfZGVzY3JpcHRvcj4KICAgICAgICAgICAgPG5hbWVfc3RyaW5nPiBIdWdvIFJhZmHDqWwgQ2jDoXZleiBGcsOtYXMgPC9uYW1lX3N0cmluZz4KICAgICAgICAgIDwvc3RyaW5nX2Rlc2NyaXB0b3I+CiAgICAgICAgPC90eXBlZF9kZXNjcmlwdG9yPgogICAgICAgIDx0eXBlZF9kZXNjcmlwdG9yPgogICAgICAgICAgPGVudHR5cGU+IFBFUiA8L2VudHR5cGU+CiAgICAgICAgICA8a2JfZGVzY3JpcHRvcj4KICAgICAgICAgICAgPGtiaWQ+IFJFRktCOjgwMDAwNTU4IDwva2JpZD4KICAgICAgICAgIDwva2JfZGVzY3JpcHRvcj4KICAgICAgICA8L3R5cGVkX2Rlc2NyaXB0b3I+CiAgICAgIDwvdHlwZWRfZGVzY3JpcHRvcnM+CiAgICA8L2VudHJ5cG9pbnQ+CiAgPC9lbnRyeXBvaW50cz4KCjwvaW5mb3JtYXRpb25fbmVlZD4K' //base64 encoding
        }),
        computed: {
            hasJobs() {
                return !!(this.jobs && this.jobs.jobs);
            },
            isButtonDisabled() {
                return this.isSubmitted || this.hasJobs;
            },
            formattedJobs() {
                return this.hasJobs ? this.jobs.jobs : null;
            }
        },
        methods: {
            submit() {
                this.count++;
                this.isSubmitted = true;
                this.history.push('Submitted ' + (this.task_type + '-' + this.algorithm).toLowerCase());
                //TODO: remove or replace SIN references
                evalClient.post('job', {
                    task_type: this.task_type,
                    algorithm: this.algorithm,
                    input_corpus: this.input_corpus,
                    sin_id: this.sin_id,
                    sin: this.sin
                }).catch(err => {
                    if (err.response) {
                        // received response so no longer submitted
                        this.isSubmitted = false;
                    }
                    this.history.push('Error: ' + JSON.stringify(err));
                });
            },
            handleRemoved(removed) {
                if (removed.job) {
                    this.jobs.jobs = this.jobs.jobs.filter(value => value !== removed.job);
                    this.history.push('Removed ' + removed.job)
                } else {
                    this.history.push(removed.err);
                }
            },
            remove(job) {
                evalClient.delete('job/' + job)
                .then(() => {
                    this.history.push('Removed ' + job);
                }).catch(err => {
                    if (err.response) {
                        // 5xx, 4xx
                    }
                    this.history.push('Error: ' + JSON.stringify(err));
                });
            },
            poll(endpoint, variable) {
                this.removePoll(variable);
                evalClient.get(endpoint)
                    .then(res => {
                        this[variable] = res.data;
                        if (this.isSubmitted && variable == 'jobs' && this.hasJobs) {
                            this.isSubmitted = false;
                        }
                    }).catch(err => {
                        this[variable] = err.response ? err.response.status : err.message + '. Please contact the aministrator';
                    }).finally(() => {
                        this.polling[variable] = setTimeout(this.poll, 3000, endpoint, variable)
                    });
            },
            removePoll(variable) {
                if (this.polling[variable]) {
                    clearTimeout(this.polling[variable]);
                    delete this.polling[variable];
                }
            },
            removePolls() {
                for (let p in this.polling) {
                    this.removePoll(p);
                }
            }
        },
        beforeDestroy() {
            this.removePolls();
        },
        created() {
            this.removePolls(); // only useful when using vue hot-update
            this.poll('job', 'jobs');
            this.poll('pod', 'pods');
        }
    }
</script>

<style scoped>
/*TODO: remove or replace SIN references*/
    .sin {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    button {
        border: 1px solid black;
        padding: 5px
    }
</style>
