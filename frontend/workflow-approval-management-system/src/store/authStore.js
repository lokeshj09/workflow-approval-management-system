export const authStore = {
    token : null ,
    setToken (token) {
        this.token = token;
    },
    getToken() {
        return this.token;
    }
}