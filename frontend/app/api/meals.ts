import api from './axios'

export async function getMeals() {
  const response = await api.get(`/meals`)
  return response.data
}
