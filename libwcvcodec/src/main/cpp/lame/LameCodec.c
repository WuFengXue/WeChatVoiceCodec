/**
 * Lame decoder and encoder
 *
 * @author Reinhard（李剑波）
 * @date 2019/6/22
 */


#include <android_log.h>
#include "include/lame.h"
#include "frontend/console.h"
#include "frontend/main.h"

static int
c_main(int argc, char *argv[])
{
    lame_t  gf;
    int     ret;

#if macintosh
    argc = ccommand(&argv);
#endif
#ifdef __EMX__
    /* This gives wildcard expansion on Non-POSIX shells with OS/2 */
    _wildcard(&argc, &argv);
#endif
#if defined( _WIN32 ) && !defined(__MINGW32__)
    set_process_affinity();
#endif

    frontend_open_console();
    gf = lame_init(); /* initialize libmp3lame */
    if (NULL == gf) {
        LOGE("fatal error during initialization\n");
        ret = 1;
    }
    else {
        ret = lame_main(gf, argc, argv);
        lame_close(gf);
    }
    frontend_close_console();
    return ret;
}

int lame_codec_main(int argc, char *argv[]) {
    return c_main(argc, argv);
}