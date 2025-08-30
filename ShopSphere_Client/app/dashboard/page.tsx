'use client'

import React from 'react'
import { MainLayout } from '@/components/layout/MainLayout'
import { useAuthStore } from '@/store/auth'
import { useRouter } from 'next/navigation'
import { useEffect } from 'react'

export default function DashboardPage() {
  const { isAuthenticated, user } = useAuthStore()
  const router = useRouter()

  useEffect(() => {
    if (!isAuthenticated) {
      router.push('/auth/login')
    }
  }, [isAuthenticated, router])

  if (!isAuthenticated) {
    return null
  }

  return (
    <MainLayout>
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>
          <p className="text-gray-600">Welcome back, {user?.firstName}!</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-900">Recent Orders</h3>
            <p className="text-3xl font-bold text-primary-600">0</p>
            <p className="text-gray-600">No orders yet</p>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-900">Wishlist Items</h3>
            <p className="text-3xl font-bold text-primary-600">0</p>
            <p className="text-gray-600">No items in wishlist</p>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-900">Cart Items</h3>
            <p className="text-3xl font-bold text-primary-600">0</p>
            <p className="text-gray-600">Cart is empty</p>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-900">Total Spent</h3>
            <p className="text-3xl font-bold text-primary-600">$0</p>
            <p className="text-gray-600">No purchases yet</p>
          </div>
        </div>
      </div>
    </MainLayout>
  )
}
