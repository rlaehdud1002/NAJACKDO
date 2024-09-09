import React, { useState } from 'react';

const interests = [
  { id: 1, name: '시', icon: '✏️' },
  { id: 2, name: '에세이', icon: '📓' },
  { id: 3, name: '소설', icon: '📖' },
  { id: 4, name: '자기계발', icon: '💪' },
  { id: 5, name: '여행', icon: '✈️' },
  { id: 6, name: '컴퓨터/IT', icon: '💻' },
  { id: 7, name: '건축', icon: '🏛️' },
  { id: 8, name: '경제/경영', icon: '💰' },
  { id: 9, name: '역사', icon: '🏺' },
  { id: 10, name: '건강/운동', icon: '🏋️‍♂️' },
  { id: 11, name: '결혼/육아', icon: '👶' },
  { id: 12, name: '사회/정치', icon: '🏛️' },
  { id: 13, name: '제테크', icon: '💵' },
  { id: 14, name: '과학', icon: '🔬' },
  { id: 15, name: '철학', icon: '🧠' },
  { id: 16, name: '디자인', icon: '🎨' },
  { id: 17, name: '예술/문화', icon: '🎭' },
  { id: 18, name: '인문학', icon: '📚' },
  { id: 19, name: '라이프스타일', icon: '🏡' },
  { id: 20, name: '마케팅', icon: '📊' },
  { id: 21, name: '종교', icon: '🙏' },
];

const Interest = () => {
  const [selectedInterests, setSelectedInterests] = useState<string[]>([]);

  // 관심 분야 선택 토글 함수
  const toggleInterest = (interest: string) => {
    if (selectedInterests.includes(interest)) {
      setSelectedInterests(selectedInterests.filter(item => item !== interest)); // 선택 해제
    } else {
      setSelectedInterests([...selectedInterests, interest]); // 선택 추가
    }
  };

  return (
    <div className='flex flex-col items-center text-2xl py-8'> 
      <header className='font-semibold'>관심 분야를 선택해 주세요.</header>
      <div className='text-[#737373] font-light mt-4 text-xs flex flex-col items-center'>
        <span>3개 이상 선택하시면</span>
        <span>분야에 맞는 책을 추천해드려요.</span>
      </div>

      
      <div className='flex flex-wrap justify-center gap-3 mt-6 text-sm'>
  {interests.map((interest) => (
    <button
      key={interest.id}
      onClick={() => toggleInterest(interest.name)}
      className={`inline-flex items-center gap-2 py-2 px-4 border rounded-full ${
        selectedInterests.includes(interest.name)
          ? 'bg-[#5F6F52] text-white'
          : 'bg-white text-gray-700 border-gray-300'
      }`}
    >
      <span>{interest.icon}</span>
      <span>{interest.name}</span> 
    </button>
  ))}
</div>
    </div>
  );
}

export default Interest;