
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import InspecticmpIcmpDataManager from "./components/listers/InspecticmpIcmpDataCards"
import InspecticmpIcmpDataDetail from "./components/listers/InspecticmpIcmpDataDetail"

import RegistrationInputInfoManager from "./components/listers/RegistrationInputInfoCards"
import RegistrationInputInfoDetail from "./components/listers/RegistrationInputInfoDetail"

import InspecthttpHttpDataManager from "./components/listers/InspecthttpHttpDataCards"
import InspecthttpHttpDataDetail from "./components/listers/InspecthttpHttpDataDetail"

import InspectdnsDnsDataManager from "./components/listers/InspectdnsDnsDataCards"
import InspectdnsDnsDataDetail from "./components/listers/InspectdnsDnsDataDetail"

import ManagementClientManager from "./components/listers/ManagementClientCards"
import ManagementClientDetail from "./components/listers/ManagementClientDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/inspecticmps/icmpData',
                name: 'InspecticmpIcmpDataManager',
                component: InspecticmpIcmpDataManager
            },
            {
                path: '/inspecticmps/icmpData/:id',
                name: 'InspecticmpIcmpDataDetail',
                component: InspecticmpIcmpDataDetail
            },

            {
                path: '/registrations/inputInfos',
                name: 'RegistrationInputInfoManager',
                component: RegistrationInputInfoManager
            },
            {
                path: '/registrations/inputInfos/:id',
                name: 'RegistrationInputInfoDetail',
                component: RegistrationInputInfoDetail
            },

            {
                path: '/inspecthttps/httpData',
                name: 'InspecthttpHttpDataManager',
                component: InspecthttpHttpDataManager
            },
            {
                path: '/inspecthttps/httpData/:id',
                name: 'InspecthttpHttpDataDetail',
                component: InspecthttpHttpDataDetail
            },

            {
                path: '/inspectdns/dnsData',
                name: 'InspectdnsDnsDataManager',
                component: InspectdnsDnsDataManager
            },
            {
                path: '/inspectdns/dnsData/:id',
                name: 'InspectdnsDnsDataDetail',
                component: InspectdnsDnsDataDetail
            },

            {
                path: '/managements/clients',
                name: 'ManagementClientManager',
                component: ManagementClientManager
            },
            {
                path: '/managements/clients/:id',
                name: 'ManagementClientDetail',
                component: ManagementClientDetail
            },



    ]
})
