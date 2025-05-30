import { NavigationContainer } from '@react-navigation/native'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import MealListScreen from './app/screens/MealListScreen'

const Stack = createNativeStackNavigator()

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Meals">
        <Stack.Screen name="Meals" component={MealListScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  )
}
