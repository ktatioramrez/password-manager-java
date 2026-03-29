const API_BASE = '/api'; 

const API = {
    async request(method, path, body = null) {
        const headers = { 'Content-Type': 'application/json' };
        const opts = { method, headers };
        if (body) opts.body = JSON.stringify(body);

        try {
            // Esto generará la URL: /api/auth/register o /api/auth/login
            const res = await fetch(`${API_BASE}${path}`, opts);
            
            const contentType = res.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                const data = await res.json();
                if (!res.ok) throw new Error(data.message || 'Error en la petición');
                return data;
            } else {
                // Si el servidor responde con HTML, es que la ruta está mal o el Servlet falló
                throw new Error('El servidor no envió JSON. Revisa la terminal de VS Code.');
            }
        } catch (error) {
            console.error("Error en API.request:", error);
            throw error;
        }
    },

    // Ajustamos las rutas para que coincidan con el @WebServlet("/api/auth/*")
    register: (data) => API.request('POST', '/auth/register', data),
    
    login: (email, password) => API.request('POST', '/auth/login', { email, password }),

    // Estas rutas también deben coincidir con tus otros Servlets (ej: /api/passwords)
    getPasswords: () => API.request('GET', '/passwords'),
    savePassword: (data) => API.request('POST', '/passwords', data),
    deletePassword: (id) => API.request('DELETE', `/passwords/${id}`)
};