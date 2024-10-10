<template>
    <v-card outlined>
        <v-card-title>
            ModifyClientInfo
        </v-card-title>

        <v-card-text>
            <Number label="ClientId" v-model="value.clientId" :editMode="editMode"/>
            <String label="ClientName" v-model="value.clientName" :editMode="editMode"/>
            <Date label="CreateDate" v-model="value.createDate" :editMode="editMode"/>
            <List&lt;Detail&gt; offline label="Details" v-model="value.details" :editMode="editMode" @change="change"/>
            <DetailList offline label="Details" v-model="value.details" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="modifyClientInfo"
            >
                ModifyClientInfo
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'ModifyClientInfoCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.clientId = 0;
            this.value.clientName = '';
            this.value.createDate = '2024-10-10';
            this.value.details = [];
        },
        watch: {
        },
        methods: {
            modifyClientInfo() {
                this.$emit('modifyClientInfo', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

