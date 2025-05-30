import React from 'react'
import { Button, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import Modal from 'react-native-modal'
import { Meal } from '../types/Meal'

interface Props {
  isVisible: boolean
  onClose: () => void
  onEdit: () => void
  meal: Meal | null
}

export default function MealDetailModal({
  isVisible,
  onClose,
  onEdit,
  meal,
}: Props) {
  if (!meal) return null

  return (
    <Modal isVisible={isVisible} onBackdropPress={onClose}>
      <View style={styles.modal}>
        <TouchableOpacity style={styles.close} onPress={onClose}>
          <Text>✕</Text>
        </TouchableOpacity>

        <Text style={styles.title}>{meal.name}</Text>
        <Text>Calories: {meal.calories}</Text>
        <Text>Protein: {meal.protein}g</Text>
        <Text>Fat: {meal.fat}g</Text>
        <Text>Carbs: {meal.carbs}g</Text>

        <Button title="Edit" onPress={onEdit} />
      </View>
    </Modal>
  )
}

const styles = StyleSheet.create({
  modal: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 12,
  },
  close: {
    position: 'absolute',
    top: 10,
    right: 10,
  },
  title: {
    fontSize: 20,
    marginBottom: 10,
  },
})
