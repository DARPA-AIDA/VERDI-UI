import {createLocalVue, shallowMount} from '@vue/test-utils'
import Vue from 'vue'
import Vuex from 'vuex'
import Vuetify from 'vuetify'
import VueRouter from 'vue-router'
import FilterMenu from './../../src/components/FilterMenu.vue'
import {DATATYPES, VUEROUTES} from '../../src/utils';
import routes from '../../src/routes';
import fetchMock from 'jest-fetch-mock';
import 'regenerator-runtime';

const localVue = createLocalVue();
localVue.use(Vuex, VueRouter);
Vue.use(Vuetify);
//Have to set data-app for Vuetify when using mount in testing
document.body.setAttribute('data-app', true);

describe('FilterMenu', () => {
    const exploreRoute = VUEROUTES.ta2;
    const dataStore = 'testDataSource';
    const deleteButtonId = '#delete_all_button';
    const submitButtonId = '#submit_button';
    const $router = new VueRouter({
        mode: 'abstract',
        routes: routes
    });

    let state;
    let mutations;
    let actions;
    let getters;
    let store;
    let vuetify;
    let data;
    let spyUpdate;

    beforeEach(() => {
        vuetify = new Vuetify();
        mutations = {
            updateFilterSubmission: jest.fn(),
            updateDataKB: jest.fn(),
            updateSavedFilters: jest.fn(),
            updateFilterReset: jest.fn()
        };
        getters = {
            getQueryParams: () => ({filter : {event: 'filters'}}),
            getStoreByRoute: () => () => dataStore,
            getFilterKeys: () => ['baseGraph', 'subtopic', 'queryClaimId'],
        };
        state = {
            currentDataSource: dataStore,
            // categoryArguments: {
            //     event : ['John Smith', 'Jane Smith', 'Joe Smith'],
            //     relation : ['Jerry Smith', 'Jonathan Smith', 'Janet Smith', 'Jim Smith']
            // },
            // categoryTypes: {
            //     event : [
            //         'ArtifactExistence.DamageDestroy.Destroy',
            //         'Conflict.Attack',
            //         'Contact.Discussion',
            //         'Life.Die',
            //         'Transaction.TransferMoney'
            //     ],
            //     relation : [
            //         'GeneralAffiliation.OrganizationPoliticalReligiousAffiliation',
            //         'OrganizationAffiliation.Leadership',
            //         'Physical.LocatedNear',
            //         'ResponsibilityBlame.AssignBlame.AssignBlame'
            //     ]
            // },
            term: {type: DATATYPES.argumentName.id},
            exploreSelections: {selectedTab: 'Event'},
            savedUrlParameters: {},
            filters: {},
            savedFilters: {}
        };
        data = {
            resetController: false
        };
        store = new Vuex.Store({
            state,
            mutations,
            actions,
            getters
        });
    });

    function createWrapper (route) {
        let $route = {name: route};

        let wrapper = shallowMount(FilterMenu, {
            localVue,
            store,
            vuetify,
            data() { return data},
            mocks: {$route, $router}
        });
        spyUpdate = jest.spyOn(wrapper.vm, 'updateRouteParams').mockImplementation(() => {});

        return wrapper;
    }

    //Initial setup
    it('The FilterMenu has data', () => {
        expect(typeof FilterMenu.data).toBe('function');
    });

    it('The delete and submit buttons are enabled', () => {
        const wrapper = createWrapper(exploreRoute);
        expect(wrapper.find(deleteButtonId).element.disabled).toBe(false);
        expect(wrapper.find(submitButtonId).element.disabled).toBe(false);
    });


    it('Clicking the delete button deletes existing filters', async () => {
        const wrapper = createWrapper(exploreRoute);
        const deleteButton = wrapper.find(deleteButtonId);
        const spyFormReset = jest.spyOn(wrapper.vm, 'resetFilterForm');
        const spyFilterReset = jest.spyOn(wrapper.vm, 'updateFilterReset');

        deleteButton.trigger('click');
        fetchMock.enableMocks();

        expect(spyFormReset).toHaveBeenCalledTimes(1);
        expect(spyFilterReset).toHaveBeenNthCalledWith(1, true);
    });

    it('Clicking the submit button submits filters', () => {
        state.filters = {
            'event':['Contact.Discussion','ArtifactExistence.DamageDestroy.Destroy'],
            'relation':['GeneralAffiliation.OrganizationPoliticalReligiousAffiliation']
        };
        const wrapper = createWrapper(exploreRoute);
        const submitButton = wrapper.find(submitButtonId);
        const spySubmit = jest.spyOn(wrapper.vm, 'submitFilters');
        submitButton.trigger('click');
        submitButton.trigger('submit.prevent');

        expect(spySubmit).toHaveBeenCalledTimes(1);
        expect(getters.getFilterKeys().length).toEqual(3);
        expect(getters.getFilterKeys()).toEqual(['baseGraph', 'subtopic', 'queryClaimId']);
        expect(spyUpdate).toHaveBeenCalledTimes(1);
        expect(mutations.updateSavedFilters).toHaveBeenCalledTimes(1);
    });

});
