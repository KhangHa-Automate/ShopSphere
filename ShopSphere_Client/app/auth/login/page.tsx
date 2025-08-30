'use client'

import React from 'react'
import { LoginForm } from '@/components/auth/LoginForm'
import { ShoppingBag } from 'lucide-react'

export default function LoginPage() {
  return (
    <div className="min-h-screen flex">
      <div className="flex-1 flex items-center justify-center lg:justify-start">
        <div className="max-w-md w-full space-y-8 px-4 sm:px-6 lg:px-8">
          <div className="text-center lg:text-left">
            <div className="flex items-center justify-center lg:justify-start mb-6">
              <ShoppingBag className="h-12 w-12 text-primary-600" />
              <h1 className="ml-3 text-3xl font-bold text-gray-900">ShopSphere</h1>
            </div>
            <h2 className="text-2xl font-bold text-gray-900">Welcome back</h2>
            <p className="mt-2 text-sm text-gray-600">
              Sign in to your account to continue shopping
            </p>
          </div>

          <LoginForm />
        </div>
      </div>

      <div className="hidden lg:block flex-1 bg-gradient-to-br from-primary-600 to-primary-800">
        <div className="h-full flex items-center justify-center">
          <div className="text-center text-white">
            <div className="mb-8">
              <ShoppingBag className="h-24 w-24 mx-auto mb-4" />
              <h1 className="text-4xl font-bold mb-4">ShopSphere</h1>
              <p className="text-xl text-primary-100 max-w-md">
                Your ultimate e-commerce destination for quality products and seamless shopping experience.
              </p>
            </div>
            
            <div className="grid grid-cols-1 gap-6 max-w-sm mx-auto">
              <div className="flex items-center space-x-3">
                <div className="w-8 h-8 bg-white bg-opacity-20 rounded-full flex items-center justify-center">
                  <span className="text-sm font-semibold">1</span>
                </div>
                <span className="text-primary-100">Browse thousands of products</span>
              </div>
              
              <div className="flex items-center space-x-3">
                <div className="w-8 h-8 bg-white bg-opacity-20 rounded-full flex items-center justify-center">
                  <span className="text-sm font-semibold">2</span>
                </div>
                <span className="text-primary-100">Secure checkout process</span>
              </div>
              
              <div className="flex items-center space-x-3">
                <div className="w-8 h-8 bg-white bg-opacity-20 rounded-full flex items-center justify-center">
                  <span className="text-sm font-semibold">3</span>
                </div>
                <span className="text-primary-100">Fast delivery to your door</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
