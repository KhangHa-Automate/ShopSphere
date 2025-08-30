export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  role: string
  createdAt: string
  updatedAt: string
}

export interface Product {
  id: number
  name: string
  description: string
  price: number
  salePrice?: number
  category: Category
  images: ProductImage[]
  variants: ProductVariant[]
  stock: number
  isActive: boolean
  createdAt: string
  updatedAt: string
}

export interface Category {
  id: number
  name: string
  description?: string
  parentId?: number
  isActive: boolean
}

export interface ProductImage {
  id: number
  url: string
  alt: string
  isPrimary: boolean
}

export interface ProductVariant {
  id: number
  name: string
  value: string
  price: number
  stock: number
}

export interface CartItem {
  id: number
  product: Product
  quantity: number
  variant?: ProductVariant
}

export interface Cart {
  id: number
  items: CartItem[]
  total: number
  status: string
}

export interface Order {
  id: number
  items: OrderItem[]
  total: number
  status: string
  shippingAddress: Address
  billingAddress: Address
  paymentStatus: string
  createdAt: string
}

export interface OrderItem {
  id: number
  product: Product
  quantity: number
  price: number
  variant?: ProductVariant
}

export interface Address {
  id: number
  firstName: string
  lastName: string
  addressLine1: string
  addressLine2?: string
  city: string
  state: string
  postalCode: string
  country: string
  phone: string
}

export interface ApiResponse<T> {
  success: boolean
  data: T
  message?: string
  errors?: string[]
}

export interface PaginatedResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
  first: boolean
  last: boolean
}
