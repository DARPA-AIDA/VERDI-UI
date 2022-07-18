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
 - The following vue component is a custom toggle button that displays hard-coded options, "ON"
 - and "OFF". This is a single switch unlike the radioButton shown as two circular buttons.
 - The mock-up example for the radio button is for the Confidence Threshold filter in the
 - filter menu located at https://f2p9s0.axshare.com/#id=pz799k&p=explore__ta1_view_&g=1
 -->

<template>
    <div class="switch" :id="id + 'ToggleSwitch'">
        <input type="checkbox" :value="switchIsOn" class="switch-checkbox" :id="id + 'SwitchInput'" @click="$emit('input', $event.target.checked)">
        <label class="switch-label" :for="id + 'SwitchInput'">
            <span class="switch-inner"></span>
            <span class="switch-switch"></span>
        </label>
    </div>
</template>

<script>
    export default {
        name: 'ToggleSwitch',
        props: {
            id: String,
            switchIsOn: Boolean
        }
    }
</script>

<style  lang="scss"  scoped>
    .switch {
        position: relative;
        flex: 0 0 60px;
    }
    .switch-checkbox {
        display: none;
    }
    .switch-label {
        display: block;
        overflow: hidden;
        cursor: pointer;
        border-radius: 20px;
        font-size: 12px;
    }
    .switch-inner {
        display: block;
        width: 200%;
        margin-left: -100%;
    }
    .switch-inner:before, .switch-inner:after {
        display: block;
        float: left;
        width: 50%;
        height: 22px;
        padding: 0;
        line-height: 22px;
        box-sizing: border-box;
    }
    .switch-inner:before {
        content: "ON";
        padding-left: 10px;
        background-color: $theme-teal;
        color: $theme-dark-light-color;
    }
    .switch-inner:after {
        content: "OFF";
        padding-right: 10px;
        background-color: $theme-dark-medium-color;
        color: $theme-dark-light-color;
        text-align: right;
    }
    .switch-switch {
        display: block;
        width: 1.3em;
        margin: 3px;
        background: $theme-dark-light-color;
        position: absolute;
        top: 0;
        bottom: 0;
        right: 36px;
        border-radius: 20px;
    }
    .switch-checkbox:checked + .switch-label .switch-inner {
        margin-left: 0;
    }
    .switch-checkbox:checked + .switch-label .switch-switch {
        right: 2px;
    }
</style>

