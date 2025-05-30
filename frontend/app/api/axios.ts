import axios from 'axios'
import { USER_ID } from '../constants/user'

const api = axios.create({
  baseURL: `http://localhost:8080/api/v0/users/${USER_ID}`,
})

export default api
