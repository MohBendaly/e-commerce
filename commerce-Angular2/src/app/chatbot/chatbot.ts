import { Component,ElementRef, ViewChild } from '@angular/core';
import { ChatService } from '../services/chatservice';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ViewEncapsulation } from '@angular/core';
@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './chatbot.html',
  styleUrls: ['./chatbot.css'],
  encapsulation: ViewEncapsulation.None
})
export class ChatComponent {
  @ViewChild('chatContainer') private chatContainer!: ElementRef;
  messages = [{ role: 'assistant', content: 'Bonjour ! Comment puis-je vous aider ?' }];
  userInput = '';
  isLoading = false;
  constructor(private chatService: ChatService) {}

  private scrollToBottom(): void {
    try {
      this.chatContainer.nativeElement.scrollTop = 
        this.chatContainer.nativeElement.scrollHeight;
    } catch(err) { }
  }
  
  send(): void {
  const message = this.userInput.trim();
  if (!message) return;
  
  this.messages.push({ role: 'user', content: message });
  this.userInput = '';
  
  this.chatService.sendMessage(message).subscribe({
  next: (reply: string) => {
    console.log('Réponse reçue :', reply); // doit afficher le texte
    this.messages.push({ role: 'assistant', content: reply });
  },
  error: (err) => {
    console.error('Erreur détaillée :', err);
    this.messages.push({ role: 'assistant', content: 'Erreur : ' + err.message });
  }
});
}

formatMessage(text: string): string {
  return text.replace(/\n/g, '<br>');
}
}