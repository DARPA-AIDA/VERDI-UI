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
 - The following vue file uses the DatePicker component in order to create a date form that
 - includes a pre-defined date range selector along with a start and end datepicker. This
 - can be easily added to the FilterMenu as a date selector. The mock-up example is the
 - date filter located at https://f2p9s0.axshare.com/#id=ou8exu&p=explore__ta2_view_
 -->

<template>
    <div class="form-wrapper">
        <label class="secondary-heading">Date</label>
        <div class="validation-field-wrapper">
            <div class="select-wrapper">
                <label class="field-label">Range Selector</label>
                <SelectDropdown :options="operatorOptions" :selectDisplay="selectDisplay" @selectDropdownChange="onDateOptionChange($event)"></SelectDropdown>
            </div>
        </div>
        <div class="validation-field-wrapper date-picker">
            <DatePicker :date="startDate" :label="startLabel" :maxDate="maxDate" @selectedFilterDate = "updateStartDate($event)"></DatePicker>
            <DatePicker :date="endDate" :label="endLabel" :maxDate="maxDate" :showDateTooltip="showTooltip" :message="endDateError"
                        @selectedFilterDate = "updateEndDate($event)"
                        @updateShowTooltip = "updateShowTooltip($event)"
            ></DatePicker>
        </div>
    </div>
</template>

<script>
    import DatePicker from '@/components/DatePicker';
    import SelectDropdown from '@/components/SelectDropdown';
    import {DATATYPES, SELECTDISPLAY} from '../utils';
    import {mapActions} from 'vuex';

    let calcYesterday = new Date();
    calcYesterday.setDate(calcYesterday.getUTCDate() - 1);
    calcYesterday.setHours(0,0,0,0);

    let calcToday = new Date();
    calcToday.setHours(23,59,59,999);

    export default {
        name: 'DateFilter',
        props: {
            showDateTooltip: Boolean
        },
        data: () => ({
            dateOperator: 0,
            operatorOptions: [
                'Between',
                'Last 30 Days',
                'Last 60 Days',
                'Last 90 Days',
                'Year'
            ],
            startDate: calcYesterday,
            endDate: calcToday,
            maxDate: calcToday,
            startLabel: 'From',
            endLabel: 'To',
            endDateError: 'Please select a valid end date.',
            showTooltip: false,
            selectDisplay: ''

        }),
        components: {DatePicker, SelectDropdown},
        created() {
            let dateObject =  {start: this.startDate, end: this.endDate};
            this.updateFilters({type: DATATYPES.dateRange.id, filter:dateObject});
            this.showTooltip = this.showDateTooltip;
            this.selectDisplay = SELECTDISPLAY.button;
        },
        methods: {
            ...mapActions(['removeFilter', 'updateFilters']),
            onDateOptionChange(event) {
                //set all start times to the beginning of the day and end times to the end of the day
                switch(event.target.value) {
                    case this.operatorOptions[0] : //Between
                        this.startDate = calcYesterday;
                        break;
                    case this.operatorOptions[1] : //30 Days
                        this.calculateNewDate(30, false);
                        break;
                    case this.operatorOptions[2] : //60 Days
                        this.calculateNewDate(60, false);
                        break;
                    case this.operatorOptions[3] : //90 Days
                        this.calculateNewDate(90, false);
                        break;
                    case this.operatorOptions[4] : //1 Year
                        this.calculateNewDate(1, true);
                        break;
                }

                this.updateDateFilters();
            },
            calculateNewDate(number, isYear) {
                let calcNewDate = new Date();
                if(isYear) {
                    calcNewDate.setFullYear(calcNewDate.getUTCFullYear() - number);
                }
                else {
                    calcNewDate.setDate(calcNewDate.getUTCDate() - number);
                }

                this.startDate = calcNewDate;
                this.startDate.setHours(0,0,0,0);
            },
            updateStartDate(start) {
                this.startDate = start;
                this.updateDateFilters();
            },
            updateEndDate(end) {
                this.endDate = end;
                this.updateDateFilters();
            },
            updateDateFilters() {
                if(this.validForm()) {
                    let dateObject =  {start: this.startDate, end: this.endDate};
                    this.updateFilters({type: DATATYPES.dateRange.id, filter:dateObject})
                }
                else {
                    this.showTooltip = true;
                    this.removeFilter({type: DATATYPES.dateRange.id});
                }
            },
            validForm() {
                return !(!(this.startDate instanceof Date) || !(this.endDate instanceof Date) ||
                    this.startDate > this.endDate);
            },
            updateShowTooltip(value) {
                this.showTooltip = value;
            }
        }
    }
</script>

<style lang="scss">
    .select-wrapper {
        @include flex-column-nowrap;
    }
</style>
