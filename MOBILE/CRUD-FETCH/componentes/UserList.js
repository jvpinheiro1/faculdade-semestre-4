import { View, Text, Button, StyleSheet } from "react-native";

export default function UserList({ users, onUserChanged }) {
    const updateUser = async (id) => {
        try{
            const response = await fetch(`https://ip:3000/users/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name: "Nome Atualizado", email: "EmailAtt@gmail.com"}),
            });
            await response.json();
            onUserChanged(); //recarregar a lista de usuarios
        }catch(error){
            console.error("Error de PUT", error.message);
        }
    }
}