export function isLoggedIn(): boolean {
    return !!localStorage.getItem("accessToken");
}
