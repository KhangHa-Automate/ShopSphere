import { api } from './api'

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  token: string
  user: {
    id: number
    email: string
    firstName: string
    lastName: string
    role: string
  }
}

export interface RegisterRequest {
  email: string
  password: string
  firstName: string
  lastName: string
}

export const authService = {
  async login(data: LoginRequest): Promise<LoginResponse> {
    const response = await api.post('/auth/login', data)
    return response.data
  },

  async register(data: RegisterRequest): Promise<LoginResponse> {
    const response = await api.post('/auth/register', data)
    return response.data
  },

  async logout(): Promise<void> {
    await api.post('/auth/logout')
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  },

  async getCurrentUser() {
    const response = await api.get('/auth/me')
    return response.data
  },
}
