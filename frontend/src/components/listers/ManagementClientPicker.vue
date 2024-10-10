<template>
    <div>
        <v-list two-line v-if="list.length > 0">
            <v-list-item-group 
                    v-model="selected" 
                    color="primary"
                    @change="select"
            >
                <v-list-item v-for="(item, idx) in list" :key="idx">
                    <template v-slot:default="{ active }">
                        <v-list-item-avatar color="primary-darker-1">
                        </v-list-item-avatar>
                        
                        <v-list-item-content>
                            <v-list-item-title>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                ClientId :  {{item.clientId }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                CreateDate :  {{item.createDate }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                ModifiDate :  {{item.modifiDate }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                ClientName :  {{item.clientName }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                TotalReq :  {{item.totalReq }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                TotalDns :  {{item.totalDns }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                TotalEtn :  {{item.totalEtn }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                LastInspectDate :  {{item.lastInspectDate }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Details :  {{item.details }}
                            </v-list-item-subtitle>
                        </v-list-item-content>

                        <v-list-item-action>
                            <v-checkbox :input-value="active" color="primary-darker-1"></v-checkbox>
                        </v-list-item-action>
                    </template>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </div>
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'ManagementClientPicker',
        props: {
            value: [String, Object, Array, Number, Boolean],
        },
        data: () => ({
            list: [],
            selected: null,
        }),
        async created() {
            var me = this;
            var temp = await axios.get(axios.fixUrl('/clients'))
            if(temp.data) {
                me.list = temp.data._embedded.clients;
            }

            if(me.value && typeof me.value == "object" && Object.values(me.value)[0]) {
                var id = Object.values(me.value)[0];
                var tmpValue = await axios.get(axios.fixUrl('/clients/' + id))
                if(tmpValue.data) {
                    var val = tmpValue.data
                    me.list.forEach(function(item, idx) {
                        if(item.name == val.name) {
                            me.selected = idx
                        }
                    })
                }
            }
        },
        methods: {
            select(val) {
                var obj = {}
                if(val != undefined) {
                    var arr = this.list[val]._links.self.href.split('/');
                    obj['clientId'] = arr[4]; 
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    this.$emit('selected', obj);
                }
            },
        },
    };
</script>

