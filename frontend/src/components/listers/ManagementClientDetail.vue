<template>
    <v-card outlined>
        <v-card-title>
            Client # {{item._links.self.href.split("/")[item._links.self.href.split("/").length - 1]}}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="ClientId" v-model="item.clientId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="CreateDate" v-model="item.createDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="ModifiDate" v-model="item.modifiDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ClientName" v-model="item.clientName" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="TotalReq" v-model="item.totalReq" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="TotalDns" v-model="item.totalDns" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="TotalEtn" v-model="item.totalEtn" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="LastInspectDate" v-model="item.lastInspectDate" :editMode="editMode" @change="change" />
            </div>
            <DetailManager offline label="Details" v-model="item.details" :editMode="false" @change="change" />
        </v-card-text>

        <v-card-actions>
            <v-btn text color="deep-purple lighten-2" large @click="goList">List</v-btn>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="edit"
                    v-if="!editMode"
            >
                Edit
            </v-btn>
            <v-btn
                    color="primary"
                    text
                    @click="save"
                    v-else
            >
                Save
            </v-btn>
            <v-btn
                    color="primary"
                    text
                    @click="remove"
                    v-if="!editMode"
            >
                Delete
            </v-btn>
            <v-btn
                    color="primary"
                    text
                    @click="editMode = false"
                    v-if="editMode"
            >
                Cancel
            </v-btn>
        </v-card-actions>
    </v-card>
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'ManagementClientDetail',
        components:{},
        props: {
        },
        data: () => ({
            item: null,
            editMode: false,
        }),
        async created() {
            var me = this;
            var params = this.$route.params;
            var temp = await axios.get(axios.fixUrl('/clients/' + params.id))
            if(temp.data) {
                me.item = temp.data
            }
        },
        methods: {
            goList() {
                var path = window.location.href.slice(window.location.href.indexOf("/#/"), window.location.href.lastIndexOf("/#"));
                path = path.replace("/#/", "/");
                this.$router.push(path);
            },
            edit() {
                this.editMode = true;
            },
            async remove(){
                try {
                    if (!this.offline) {
                        await axios.delete(axios.fixUrl(this.item._links.self.href))
                    }

                    this.editMode = false;

                    this.$emit('input', this.item);
                    this.$emit('delete', this.item);

                } catch(e) {
                    console.log(e)
                }
            },
        },
    };
</script>
