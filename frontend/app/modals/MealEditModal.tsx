import React, { useEffect, useState } from 'react'
import {
  Button,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native'
import Modal from 'react-native-modal'

type FormField = 'name' | 'calories' | 'protein' | 'fat' | 'carbs'
type MealForm = Record<FormField, string>

interface Meal {
  id: string
  name: string
  calories: number
  protein: number
  fat: number
  carbs: number
}

interface Props {
  isVisible: boolean
  onClose: () => void
  onSave: (updatedMeal: Meal) => void
  meal: Meal | null
}

export default function MealEditModal({
  isVisible,
  onClose,
  onSave,
  meal,
}: Props) {
  // ✅ Place this here:
  const [form, setForm] = useState<MealForm>({
    name: '',
    calories: '',
    protein: '',
    fat: '',
    carbs: '',
  })

  useEffect(() => {
    if (meal) {
      setForm({
        name: meal.name,
        calories: meal.calories.toString(),
        protein: meal.protein.toString(),
        fat: meal.fat.toString(),
        carbs: meal.carbs.toString(),
      })
    }
  }, [meal])

  const handleSave = () => {
    if (!meal) return
    const updatedMeal: Meal = {
      ...meal,
      name: form.name,
      calories: parseFloat(form.calories),
      protein: parseFloat(form.protein),
      fat: parseFloat(form.fat),
      carbs: parseFloat(form.carbs),
    }
    onSave(updatedMeal)
  }

  if (!meal) return null

  return (
    <Modal isVisible={isVisible} onBackdropPress={onClose}>
      <View style={styles.modal}>
        <TouchableOpacity style={styles.close} onPress={onClose}>
          <Text>✕</Text>
        </TouchableOpacity>

        <Text style={styles.title}>Edit Meal</Text>

        {(['name', 'calories', 'protein', 'fat', 'carbs'] as FormField[]).map(
          (field) => (
            <TextInput
              key={field}
              placeholder={field}
              value={form[field]}
              onChangeText={(text) => setForm({ ...form, [field]: text })}
              keyboardType={field === 'name' ? 'default' : 'numeric'}
              style={styles.input}
            />
          ),
        )}

        <Button title="Save" onPress={handleSave} />
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
  input: {
    borderBottomWidth: 1,
    padding: 8,
    marginVertical: 6,
  },
})
