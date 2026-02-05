// src/app/profiles/me/page.tsx
"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getMyProfile } from "@/lib/api/profile/profile.api";
import { ProfilePage } from "@/modules/profile";
import { ProfileResponse } from "@/lib/api/profile/profile.api";

export default function MyProfilePage() {
    const [profile, setProfile] = useState<ProfileResponse | null>(null);
    const router = useRouter();

    useEffect(() => {
        getMyProfile()
            .then((res) => {
                if (!res.success || !res.data) {
                    router.replace("/auth/login");
                } else {
                    setProfile(res.data);
                }
            })
            .catch(() => {
                router.replace("/auth/login");
            });
    }, [router]);

    if (!profile) return null; // ya da loader

    return <ProfilePage profile={profile} isOwner={true} />;
}
