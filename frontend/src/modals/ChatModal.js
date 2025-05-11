import React, { useState, useRef, useEffect } from 'react';
import { useSelector } from 'react-redux';

const ChatModal = ({ setIsChatModalOpen }) => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const messagesEndRef = useRef(null);
  const boards = useSelector((state) => state.boards);
  const board = boards.find((board) => board.isActive);

  const formatAIResponse = (text) => {
    // Remove asterisks and format the text
    return text
      .replace(/\*\*/g, '') // Remove double asterisks
      .split('\n') // Split into lines
      .map(line => line.trim()) // Trim each line
      .filter(line => line.length > 0) // Remove empty lines
      .join('\n');
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  // Function to fetch project status
  const fetchProjectStatus = async () => {
    setIsLoading(true);
    try {
      const projectId = 1;
      console.log(projectId);
      const response = await fetch(`http://localhost:8080/api/v1/projects/health/${projectId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = await response.json();
      
      if (data.success) {
        const geminiResponse = JSON.parse(data.data);
        const aiMessage = geminiResponse.candidates[0].content.parts[0].text;
        const formattedMessage = formatAIResponse(aiMessage);
        
        setMessages(prev => [...prev, { 
          role: 'assistant', 
          content: formattedMessage 
        }]);
      } else {
        throw new Error(data.message || 'Failed to get project health status');
      }
    } catch (error) {
      console.error('Error fetching project status:', error);
      setMessages(prev => [...prev, { 
        role: 'assistant', 
        content: 'I encountered an error while fetching the project status. Please try again.' 
      }]);
    } finally {
      setIsLoading(false);
    }
  };

  // Call API when modal opens
  useEffect(() => {
    fetchProjectStatus();
  }, []); // Empty dependency array means this runs once when component mounts

  const handleSendMessage = async () => {
    if (!inputMessage.trim()) return;

    // Add user message to chat
    const userMessage = { role: 'user', content: inputMessage };
    setMessages(prev => [...prev, userMessage]);
    setInputMessage('');
    setIsLoading(true);

    try {
      const projectId = 1;
      const response = await fetch(`http://localhost:8080/api/v1/projects/${projectId}/chat?prompt=${encodeURIComponent(inputMessage)}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = await response.json();
      
      if (data.success) {
        const geminiResponse = JSON.parse(data.data);
        const aiMessage = geminiResponse.candidates[0].content.parts[0].text;
        const formattedMessage = formatAIResponse(aiMessage);
        
        setMessages(prev => [...prev, { 
          role: 'assistant', 
          content: formattedMessage 
        }]);
      } else {
        throw new Error(data.message || 'Failed to get response from chat');
      }
    } catch (error) {
      console.error('Error sending message:', error);
      setMessages(prev => [...prev, { 
        role: 'assistant', 
        content: 'I encountered an error while processing your request. Please try again.' 
      }]);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white dark:bg-[#2b2c37] rounded-lg w-[90%] max-w-2xl h-[80vh] flex flex-col">
        {/* Header */}
        <div className="p-4 border-b dark:border-gray-700 flex justify-between items-center">
          <h2 className="text-xl font-bold dark:text-white">Project Health Chat</h2>
          <button
            onClick={() => setIsChatModalOpen(false)}
            className="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
          >
            ✕
          </button>
        </div>

        {/* Messages Container */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {messages.map((message, index) => (
            <div
              key={index}
              className={`flex ${message.role === 'user' ? 'justify-end' : 'justify-start'}`}
            >
              <div
                className={`max-w-[70%] rounded-lg p-3 ${
                  message.role === 'user'
                    ? 'bg-blue-500 text-white'
                    : 'bg-gray-200 dark:bg-gray-700 dark:text-white'
                }`}
              >
                <pre className="whitespace-pre-wrap font-sans">{message.content}</pre>
              </div>
            </div>
          ))}
          {isLoading && (
            <div className="flex justify-start">
              <div className="bg-gray-200 dark:bg-gray-700 dark:text-white rounded-lg p-3">
                Analyzing project status...
              </div>
            </div>
          )}
          <div ref={messagesEndRef} />
        </div>

        {/* Input Area */}
        <div className="p-4 border-t dark:border-gray-700">
          <div className="flex space-x-2">
            <input
              type="text"
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && !isLoading && handleSendMessage()}
              placeholder="Ask about project health..."
              className="flex-1 p-2 border rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white"
              disabled={isLoading}
            />
            <button
              onClick={handleSendMessage}
              disabled={isLoading}
              className={`px-4 py-2 rounded-lg ${
                isLoading 
                  ? 'bg-gray-400 cursor-not-allowed' 
                  : 'bg-blue-500 hover:bg-blue-600'
              } text-white`}
            >
              {isLoading ? 'Sending...' : 'Send'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatModal; 