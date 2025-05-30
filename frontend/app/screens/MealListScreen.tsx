import React, { useEffect, useState } from 'react'
import {
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native'
import { getMeals } from '../api/meals'
import MealDetailModal from '../modals/MealDetailModal'
import MealEditModal from '../modals/MealEditModal'
import { Meal } from '../types/Meal'

export default function MealListScreen() {
  const [meals, setMeals] = useState<Meal[]>([])
  const [selectedMeal, setSelectedMeal] = useState<Meal | null>(null)
  const [showDetailModal, setShowDetailModal] = useState(false)
  const [showEditModal, setShowEditModal] = useState(false)

  useEffect(() => {
    getMeals().then(setMeals)
  }, [])

  const handleUpdateMeal = (updatedMeal: Meal) => {
    setMeals((prev) =>
      prev.map((m) => (m.id === updatedMeal.id ? updatedMeal : m)),
    )
    setSelectedMeal(updatedMeal)
    setShowEditModal(false)
    setShowDetailModal(true)
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Your Meals</Text>
      <FlatList
        data={meals}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.item}
            onPress={() => {
              setSelectedMeal(item)
              setShowDetailModal(true)
            }}
          >
            <Text>{item.name}</Text>
          </TouchableOpacity>
        )}
      />

      <MealDetailModal
        isVisible={showDetailModal}
        meal={selectedMeal}
        onClose={() => setShowDetailModal(false)}
        onEdit={() => {
          setShowDetailModal(false)
          setShowEditModal(true)
        }}
      />

      <MealEditModal
        isVisible={showEditModal}
        meal={selectedMeal}
        onClose={() => {
          setShowEditModal(false)
          setShowDetailModal(true) // reopen with same data
        }}
        onSave={handleUpdateMeal}
      />
    </View>
  )
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 16 },
  title: { fontSize: 24, marginBottom: 12 },
  item: {
    padding: 12,
    borderBottomWidth: 1,
  },
})
