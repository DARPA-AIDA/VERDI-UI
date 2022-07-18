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
 - The following vue component is a custom radio button display that can be used when
 - only one of two choices can be selected (ON/OFF, TRUE/FALSE, YES/NO). The functionality
 - is similar to the toggle switch, but choices can be customized.
 -->

<template>
    <div>
        <span class="hand-cursor custom-radio-input" v-for="(label, index) in labels" :key="label">
            <span class="custom-radio-button">
                <input type="radio" :ref="getRefId(id)" :name="getRefId(id)" :value="label" :checked="index===0"  @change="$emit('radioButtonChange', $event.target.value)">
            </span>
            <span :id="id + label" @click="updateRadioButton(id, index, label)">{{label}}</span>
        </span>
    </div>
</template>

<script>
    export default {
        name: 'RadioButtonDisplay',
        props: {
            id: String,
            labels: Array
        },
        mounted() {
            this.$emit('radioButtonChange', this.labels[0]);
        },
        methods: {
            getRefId(id) {
                return id + 'Radio';
            },
            updateRadioButton(id, index, label) {
                let radioReference = this.$refs[this.getRefId(id)];
                if(radioReference) {
                    for (let i = 0; i < radioReference.length; i++ ) {
                        radioReference[i].checked = index === i;
                    }
                    this.$emit('radioButtonChange', label)
                }
            }
        }
    }
</script>
