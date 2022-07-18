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
 - The following vue file is used in the DateFilter form, but can also be used
 - independently as a way to select date using a calendar selector.
 -->

<template>
    <div class="date-picker-wrapper">
        <label class="field-label">{{label}}</label>
        <date-time-picker class="light-app-button"
                          format="yyyy-LL-dd"
                          :value="dateSelection"
                          :close-button="true"
                          :max-date="maxDate"
                          @input="$emit('selectedFilterDate', $event)">
        </date-time-picker>
        <ValidationTooltip v-if="message" :icon="'mdi-alert-box'" :showTooltip="showTooltip" :message="message" @updateShowTooltip="updateShowTooltip($event)"></ValidationTooltip>
        <label class="field-label">yyyy-mm-dd</label>
    </div>
</template>

<script>
    import DateTimePicker from 'vue-vanilla-datetime-picker';
    import arrow from '@/assets/images/select-arrow.svg';
    import ValidationTooltip from '@/components/ValidationTooltip';

    export default {
        name: 'DatePicker',
        props: {
            date: Date,
            maxDate: Date,
            label: String,
            showDateTooltip: Boolean,
            message: String
        },
        data: () => ({
            selectedDate: null,
            showTooltip: false,
            arrow
        }),
        components: { DateTimePicker, ValidationTooltip },
        computed: {
            dateSelection: {
                get() {
                    return (this.selectedDate === null) ? this.date : this.selectedDate
                },
                set(selection) {
                    this.selectedDate = selection;
                }
            }
        },
        watch: {
            showDateTooltip(value) {
                if(value) {
                    this.showTooltip = value;
                }
            }
        },
        methods: {
            updateShowTooltip(value) {
                this.showTooltip = value;
                this.$emit('updateShowTooltip', this.showTooltip );
            }
        }
    }
</script>

<style lang="scss"> //do not scope these styles because 'button:after' needs to apply to component using the DatePicker
    .date-picker-wrapper {
        display: flex;
        flex-flow: column nowrap;
        align-content: center;
        padding: 4px;
        justify-content: space-around;
    }

    .date-picker-wrapper .datetime-picker-main{
        position: absolute !important;
    }

    .light-app-button > button:after{
        font-family: 'Material Icons', sans-serif;
        content: "calendar_today";
        color: $theme-teal;
        vertical-align: bottom;
    }
</style>
