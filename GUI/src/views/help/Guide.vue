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
  - Created by s.nordt on 5/9/22
-->

<template>
    <div class="guide-page-content">
        <p class="guide-header">AIDA Help Guide</p>
        <div class="guide-section">
            <div class="guide-section-header">Claims</div>
            <p><span class="bold-text">Claims</span> are a way for the gathered data to be organized and presented in a human readable format.
                Each claim follows a template of topic, subtopic and x-variable which is called a Claim Frame Template.</p>
        </div>
        <div class="guide-section">
            <div class="guide-section-header">Topic, Subtopic and X-variable</div>
            <p>Topics are the main categories for the type of information.
                <br><span class="guide-example">Example Topic: COVID-19</span>
            </p>
            <p>Subtopics are the branches of information from a specific topic.
                <br><span class="guide-example">Example Subtopic: How does COVID-19 spread?</span>
            </p>
            <p>X-variables are the answers to the subtopic questions that are gathered from data sources such as news outlets. There are typically multiple x-variables.
                <br><span class="guide-example">Example X-variable: Droplets from infected persons</span>
            </p>
            <p>So putting a claim altogether, it will look something like this:
                <br><span class="guide-example">Topic: COVID-19</span>
                <br><span class="guide-example">Subtopic: How does COVID-19 spread?</span>
                <br><span class="guide-example">X-variable:</span>
            </p>
        </div>
        <div class="guide-section">
            <div class="guide-section-header">Claim Anatomy</div>
            <p>Claims are composed of gathered data from many types of sources <span class="guide-highlighted-text">(more info about sources?)</span>. AIDA will extract data indiscriminately,
                pulling from a source whether it is considered trusted or otherwise. The sources are separated by several attributes called Claim Frame Details.
                Claim Frame details are made up of:</p>
            <p class="guide-label-description"><span>Topic</span><span>The category of information at the highest level</span></p>
            <p class="guide-label-description"><span>Subtopic</span><span>More specific information from the topic</span></p>
            <p class="guide-label-description"><span>X-variable</span><span>The word or phrase that answers the topic/subtopic</span></p>
            <p class="guide-label-description"><span>Description</span><span>A short statement about the claim being made</span></p>
            <p class="guide-label-description"><span>Location</span><span>The geolocation of where the claim is being made</span></p>
            <p class="guide-label-description"><span>Medium</span><span>The format of the claim (news article, blog post, tweet, etc.)</span></p>
            <p class="guide-label-description"><span>Date</span><span>The date the claim was made</span></p>
            <p class="guide-label-description"><span>Epistemic</span><span>The algorithm to determine how truthful the source document is</span></p>
            <p class="guide-label-description"><span>Sentiment</span><span>Determines if the source document is positive, negative or neutral</span></p>
            <p class="guide-label-description"><span>Claimer</span><span>The author of the claim</span></p>
            <p class="guide-label-description"><span>Source Document</span><span>A safe link to the root document itself, where the claim is being extracted from</span></p>
        </div>
        <div class="guide-section"><p>Along with these attributes, AIDA will also present other claims frames as well.</p></div>
        <div class="guide-section">
            <p class="guide-label-description"><span>Identical Claims</span><span class="guide-highlighted-text">Need clarification on this</span></p>
            <p class="guide-label-description"><span>Supporting Claims</span><span class="guide-highlighted-text">Need clarification on this</span></p>
            <p class="guide-label-description"><span>Related Claims</span><span class="guide-highlighted-text">Need clarification on this</span></p>
            <p class="guide-label-description"><span>Refuting Claims</span><span>Claims that give information and statements opposite of the claim</span></p>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Guide'
    }
</script>

<style lang="scss" scoped>
    .guide-page-content{
        @extend .page-content;
        color: black;
    }
    .guide-header{
        color: $theme-teal;
        font-size: 33px;
        width: 100%;
        margin-top: 32px;
    }
    .guide-section {
        padding-bottom: 14px;
    }
    .guide-section-header{
        margin: 10px 0;
        @extend .primary-heading;
        color: $theme-teal;
    }
    .guide-example{
        font-size: 12px;
        color: $theme-primary-color;
    }
    .guide-highlighted-text{
        color:$theme-gold !important;
    }

    .guide-label-description{
        @include flex-row-nowrap;

        span:nth-child(1){
            width: 135px;
            display: flex;
            flex: 0 0 auto;
        }
        span:nth-child(2){
            color: $theme-primary-color;
            @include flex-row-wrap;
        }
    }
</style>

