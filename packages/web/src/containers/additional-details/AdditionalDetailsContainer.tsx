'use client'

import { useRouter } from 'next/navigation'

import { useState } from 'react'

import LikeGameForm from './AdditionalGames'
import MBTIForm from './MBTIForm'
import { ItemType } from './state'

import FormLayout from '@/components/form/formLayout'
import { getGames } from '@/apis/game'

// TODO: 다음 버튼 유효성 검증 및 disabled
export default function AdditionalDetailsContainer({
  className,
  step,
}: {
  className?: string
  step: string
}) {
  const router = useRouter()
  const [mbti, setMbti] = useState('')
  const [selectedGames, setSelectedGames] = useState<ItemType[]>([])

  const handleButtonClick = (item: ItemType) => {
    setSelectedGames((prev) => {
      if (prev.includes(item)) {
        return prev.filter((game) => game.index !== item.index)
      }
      // TODO: 오류 문구 출력 ?
      if (prev.length === 3) {
        return prev
      }
      return [...prev, item]
    })
  }

  const handleNextStep = () => {
    const nextStep = parseInt(step, 10) + 1
    if (nextStep <= 3) {
      router.replace(`/additional-details?step=${nextStep}`)
    } else if (nextStep === 4) {
      console.log('likeGames', selectedGames)
      console.log('mbti:', mbti)
      console.log('저장하고 닫기')
    }
  }

  const handlePrevStep = () => {
    const prevStep = parseInt(step, 10) - 1
    if (prevStep > 0) {
      router.replace(`/additional-details?step=${prevStep}`)
    }
  }

  const getGame = async () => {
    const res = await getGames()
    console.log(res)
  }

  getGame()

  if (Number(step) === 2) {
    return (
      <FormLayout className={`${className}`}>
        <FormLayout.Legend
          title="SIGN UP?"
          description={`대표 게임을\n 설정해주세요`}
        />
        <section className="border h-36">{step} 폼쓰</section>

        <FormLayout.PrevNextButton
          onPrevClick={handlePrevStep}
          onNextClick={handleNextStep}
        />
        <FormLayout.PassButton />
      </FormLayout>
    )
  }

  if (Number(step) === 3) {
    return (
      <FormLayout className={`${className}`}>
        <FormLayout.Legend
          title="SIGN UP?"
          description={`MBTI를\n 선택해주세요`}
        />

        <MBTIForm mbti={mbti} onChange={(value: string) => setMbti(value)} />

        <FormLayout.PrevNextButton
          onPrevClick={handlePrevStep}
          onNextClick={handleNextStep}
        />
        <FormLayout.PassButton />
      </FormLayout>
    )
  }

  return <div>잘못된 URL</div>
}
