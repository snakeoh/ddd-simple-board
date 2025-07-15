export const getAccessToken = () => localStorage.getItem("accessToken");
export const setAccessToken = (token: string) => localStorage.setItem("accessToken", token);

export function logout() {
    localStorage.removeItem("accessToken");
    window.location.href = "/login";
}
