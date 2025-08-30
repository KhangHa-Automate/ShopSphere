import { api } from '../api'
import { Product, PaginatedResponse } from '@/types'

export const productService = {
  async getProducts(page = 0, size = 12, category?: string, search?: string): Promise<PaginatedResponse<Product>> {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
    })
    
    if (category) params.append('category', category)
    if (search) params.append('search', search)
    
    const response = await api.get(`/products?${params}`)
    return response.data
  },

  async getProduct(id: number): Promise<Product> {
    const response = await api.get(`/products/${id}`)
    return response.data
  },

  async getProductsByCategory(categoryId: number, page = 0, size = 12): Promise<PaginatedResponse<Product>> {
    const response = await api.get(`/categories/${categoryId}/products`, {
      params: { page, size }
    })
    return response.data
  },

  async searchProducts(query: string, page = 0, size = 12): Promise<PaginatedResponse<Product>> {
    const response = await api.get('/products/search', {
      params: { q: query, page, size }
    })
    return response.data
  },

  async getFeaturedProducts(): Promise<Product[]> {
    const response = await api.get('/products/featured')
    return response.data
  },

  async getNewArrivals(): Promise<Product[]> {
    const response = await api.get('/products/new-arrivals')
    return response.data
  },

  async getOnSaleProducts(): Promise<Product[]> {
    const response = await api.get('/products/on-sale')
    return response.data
  }
}
